#!/bin/bash
#
# Copies the installers built by the GitHub workflow "Installers" to the download server.
#
#   publish-installers.sh --version 3.0 [--run <id>|latest] [--dry-run] [--force]
#
# The installers are the artifacts installers-Linux, installers-macOS and installers-Windows of a
# successful run of the workflow. They land on the download server as
#   <root>/<version>/<yyyy-MM-dd>/{Linux,Mac,Windows}/<files> + SHA256SUMS
# The version is used as given (3.0-SNAPSHOT stays 3.0-SNAPSHOT; the former Maven chain removed the dashes).
#
# Needs curl and python3 (the GitHub REST API is called directly, gh is not required).
#
# Environment:
#   GH_TOKEN         GitHub token, to download the artifacts (any valid token for a public repository)
#   DOWNLOAD_TARGET  user@host of the download server (required unless --dry-run)
#   DOWNLOAD_ROOT    directory on that server, default ~/downloads/openflexo
#   REPO             default openflexo-team/openflexo-packaging
#   BRANCH           branch whose latest successful run is taken by "latest", default 3.0
#
# Options:
#   --dry-run          download and stage, print what would be sent, send nothing, touch nothing remote
#   --force            accept that the destination directory already exists on the server (it is overwritten)
#   --date <date>      directory date, default today
#   --from-dir <dir>   use artifacts already downloaded in <dir> (one directory per artifact), for tests
#   --resolve-only     print the run that would be used and stop

set -euo pipefail

REPO="${REPO:-openflexo-team/openflexo-packaging}"
BRANCH="${BRANCH:-3.0}"
WORKFLOW="installers.yml"
DOWNLOAD_ROOT="${DOWNLOAD_ROOT:-~/downloads/openflexo}"

version="" ; run="latest" ; dry=0 ; force=0 ; date_dir="$(date +%Y-%m-%d)" ; from_dir="" ; resolve_only=0

die() { echo "publish-installers: $*" >&2 ; exit 1 ; }

while [ $# -gt 0 ]; do
  case "$1" in
    --version) version="${2:?}" ; shift 2 ;;
    --run) run="${2:?}" ; shift 2 ;;
    --dry-run) dry=1 ; shift ;;
    --force) force=1 ; shift ;;
    --date) date_dir="${2:?}" ; shift 2 ;;
    --from-dir) from_dir="${2:?}" ; shift 2 ;;
    --resolve-only) resolve_only=1 ; shift ;;
    -h|--help) sed -n '2,30p' "$0" ; exit 0 ;;
    *) die "unknown option $1" ;;
  esac
done

[ -n "$version" ] || die "--version is required"
echo "$date_dir" | grep -Eq '^[0-9]{4}-[0-9]{2}-[0-9]{2}$' || die "--date must read yyyy-MM-dd"
remote_version="$version"

# --- which run -------------------------------------------------------------------------------
workdir="$(mktemp -d)"
trap 'rm -rf "$workdir"' EXIT

# The GitHub REST API, with curl; python3 reads the JSON
api="https://api.github.com/repos/$REPO"
# A transfer slower than 10 kB/s for 60 seconds is a stall: curl gives up on it, tries again and,
# for a file, resumes where it stopped (-C -). Seen: a download stuck at 42% for 9 minutes.
# --retry-all-errors needs curl 7.71 (the Jenkins agent has 7.64): used only when curl knows it, the retry loop below
# and --retry (which covers timeouts) do the rest.
retry_all=""
curl --help all 2>/dev/null | grep -q -- '--retry-all-errors' && retry_all="--retry-all-errors"
gh_get() {   # gh_get <url> [output file]: the JSON of the API, or a file when redirected to the storage
  curl -sSfL --connect-timeout 30 --speed-limit 10240 --speed-time 60 \
       --retry 5 --retry-delay 5 $retry_all ${2:+-C -} \
       -H "Authorization: Bearer ${GH_TOKEN:?GH_TOKEN is required}" -H "Accept: application/vnd.github+json" \
       -H "X-GitHub-Api-Version: 2022-11-28" "$1" ${2:+-o "$2"}
}
json() { python3 -c "import json,sys; d=json.load(sys.stdin); print($1)"; }
api_json() {   # api_json <url> <python expression on d>: fails with a clear message when the call does not succeed
  local body
  body="$(gh_get "$1")" || die "GitHub API call failed: ${1#https://api.github.com}"
  printf '%s' "$body" | json "$2"
}

if [ -z "$from_dir" ]; then
  command -v curl >/dev/null || die "curl is required"
  command -v python3 >/dev/null || die "python3 is required"
  if [ "$run" = "latest" ]; then
    run="$(api_json "$api/actions/workflows/$WORKFLOW/runs?branch=$BRANCH&status=success&per_page=1" \
            "d['workflow_runs'][0]['id'] if d['workflow_runs'] else ''")"
    [ -n "$run" ] || die "no successful run of $WORKFLOW on $BRANCH"
  fi
  info="$(api_json "$api/actions/runs/$run" "' '.join([str(d['conclusion']), d['head_sha'], d['head_branch'], d['created_at']])")"
  set -- $info
  [ "$1" = "success" ] || die "run $run did not succeed (conclusion: $1)"
  echo "Run $run: commit $2, branch $3, created $4"
  [ "$resolve_only" = 1 ] && exit 0

  echo "Downloading the artifacts of run $run ..."
  api_json "$api/actions/runs/$run/artifacts?per_page=100" \
    "'\\n'.join('%s %s %s' % (a['id'], a['size_in_bytes'], a['name']) for a in d['artifacts'] if not a['expired'])" > "$workdir/artifacts.list"
  [ -s "$workdir/artifacts.list" ] || die "run $run has no artifact left (they expire after 90 days)"
  from_dir="$workdir/artifacts"
  total="$(wc -l < "$workdir/artifacts.list" | tr -d ' ')" ; n=0
  while read -r id size name; do
    n=$((n + 1))
    echo "[$n/$total] $name ($((size / 1048576)) MB)"
    # A progress line every 10 seconds: the console of a job shows nothing of a silent curl
    # up to 3 attempts, each one resuming the partial file of the previous one
    attempt=1
    while :; do
      gh_get "$api/actions/artifacts/$id/zip" "$workdir/$name.zip" &
      curl_pid=$!
      last=-1
      while kill -0 "$curl_pid" 2>/dev/null; do
        sleep 10
        got="$(wc -c < "$workdir/$name.zip" 2>/dev/null | tr -d ' ')" ; got="${got:-0}"
        # a line only when something moved
        [ "$got" != "$last" ] && [ "$size" -gt 0 ] && echo "    $((got / 1048576)) MB of $((size / 1048576)) MB ($((got * 100 / size))%)"
        last="$got"
      done
      wait "$curl_pid" && break
      [ "$attempt" -lt 3 ] || die "download of $name failed after $attempt attempts"
      attempt=$((attempt + 1))
      echo "    interrupted, resuming (attempt $attempt of 3)"
    done
    echo "    unpacking"
    python3 -c "import zipfile,sys; zipfile.ZipFile(sys.argv[1]).extractall(sys.argv[2])" "$workdir/$name.zip" "$from_dir/$name"
    rm -f "$workdir/$name.zip"
  done < "$workdir/artifacts.list"
else
  [ -d "$from_dir" ] || die "$from_dir is not a directory"
  [ "$resolve_only" = 1 ] && { echo "Artifacts taken from $from_dir"; exit 0; }
fi

# --- stage -----------------------------------------------------------------------------------
# The layout inside an artifact is not relied upon: installers are found by extension.
stage="$workdir/stage/$date_dir"
for pair in Linux:installers-Linux Mac:installers-macOS Windows:installers-Windows; do
  os="${pair%%:*}" ; artifact="${pair##*:}"
  src="$from_dir/$artifact"
  [ -d "$src" ] || die "artifact $artifact is missing"
  mkdir -p "$stage/$os"
  find "$src" -type f \( -name '*.dmg' -o -name '*.exe' -o -name '*.msi' -o -name '*.deb' -o -name '*.rpm' -o -name '*.tar.gz' \) \
       -exec cp {} "$stage/$os/" \;
  count="$(find "$stage/$os" -type f | wc -l | tr -d ' ')"
  [ "$count" -gt 0 ] || die "no installer found in $artifact"
  ( cd "$stage/$os" && { sha256sum -- * 2>/dev/null || shasum -a 256 -- * ; } > SHA256SUMS )
  echo "Staged $os: $count file(s)"
  ls -l "$stage/$os" | sed 's/^/    /'
done

# --- send ------------------------------------------------------------------------------------
dest="$DOWNLOAD_ROOT/$remote_version"
# Same as the former Ant chain (scp trust="true"): no question asked, a new host key is accepted
ssh_opts="-o BatchMode=yes -o StrictHostKeyChecking=accept-new"
if [ "$dry" = 1 ]; then
  echo "Dry run: nothing sent. Would copy $stage to ${DOWNLOAD_TARGET:-<DOWNLOAD_TARGET>}:$dest/$date_dir"
  exit 0
fi

[ -n "${DOWNLOAD_TARGET:-}" ] || die "DOWNLOAD_TARGET is required"
if ssh $ssh_opts "$DOWNLOAD_TARGET" "test -e $dest/$date_dir"; then
  [ "$force" = 1 ] || die "$dest/$date_dir already exists on the server (use --force to overwrite)"
fi
ssh $ssh_opts "$DOWNLOAD_TARGET" "mkdir -p $dest"
scp $ssh_opts -r "$stage" "$DOWNLOAD_TARGET:$dest/"
echo "Published in $DOWNLOAD_TARGET:$dest/$date_dir"
