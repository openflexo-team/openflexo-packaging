# Image of the Openflexo server. The build context is the content of the portable archive of the
# server (distTar: bin/ and lib/ at its root), see the workflow .github/workflows/installers.yml:
#   docker build -f Dockerfile <directory of the unpacked archive>
FROM eclipse-temurin:17-jre

RUN useradd --system --create-home --home-dir /openflexo openflexo
COPY --chown=openflexo . /opt/openflexo-server

# Resource center volume, registered by default
RUN mkdir /rc && chown openflexo /rc
VOLUME /rc

USER openflexo
WORKDIR /openflexo
EXPOSE 9090

# The server listens on localhost by default, which is not reachable from outside the container.
# Arguments given to "docker run" replace CMD (--port, --center, --project, --preload...).
ENTRYPOINT ["/opt/openflexo-server/bin/openflexo-server", "--host", "0.0.0.0"]
CMD ["--port", "9090", "--center", "/rc"]
