#!/bin/bash
#
# Copies the installers built by the GitHub workflow "Installers" to the download server.
#
#   publish-installers.sh --version 3.0 [--run <id>|latest] [--dry-run] [--force]
#
# The installers are the artifacts installers-Linux, installers-macOS and installers-Windows of a
# successful run of the workflow. They land on the download server as
#   <root>/<version without dashes>/<yyyy-MM-dd>/{Linux,Mac,Windows}/<files> + SHA256SUMS
# like the ones of the former Maven chain.
#
# Environment:
#   GH_TOKEN         token able to read the artifacts of the repository (permission "Actions: read"), read by gh
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
    -h|--help) sed -n '2,29p' "$0" ; exit 0 ;;
    *) die "unknown option $1" ;;
  esac
done

[ -n "$version" ] || die "--version is required"
echo "$date_dir" | grep -Eq '^[0-9]{4}-[0-9]{2}-[0-9]{2}$' || die "--date must read yyyy-MM-dd"
remote_version="${version//-/}"     # 3.0-SNAPSHOT -> 3.0SNAPSHOT, as the former chain did

# --- which run -------------------------------------------------------------------------------
workdir="$(mktemp -d)"
trap 'rm -rf "$workdir"' EXIT

if [ -z "$from_dir" ]; then
  command -v gh >/dev/null || die "gh is required"
  if [ "$run" = "latest" ]; then
    run="$(gh run list --repo "$REPO" --workflow "$WORKFLOW" --branch "$BRANCH" --status success --limit 1 \
            --json databaseId --jq '.[0].databaseId')"
    [ -n "$run" ] && [ "$run" != "null" ] || die "no successful run of $WORKFLOW on $BRANCH"
  fi
  info="$(gh run view "$run" --repo "$REPO" --json conclusion,headSha,headBranch,createdAt \
           --jq '"\(.conclusion) \(.headSha) \(.headBranch) \(.createdAt)"')"
  set -- $info
  [ "$1" = "success" ] || die "run $run did not succeed (conclusion: $1)"
  echo "Run $run: commit $2, branch $3, created $4"
  [ "$resolve_only" = 1 ] && exit 0

  echo "Downloading the artifacts of run $run ..."
  gh run download "$run" --repo "$REPO" --dir "$workdir/artifacts"
  from_dir="$workdir/artifacts"
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
  ( cd "$stage/$os" && shasum -a 256 -- * > SHA256SUMS )
  echo "$os: $count file(s)"
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
