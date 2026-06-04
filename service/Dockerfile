FROM ubuntu:latest

COPY package.json tsconfig.json /service/
COPY scripts /service/scripts
COPY src /service/src

ENTRYPOINT [ "/service/scripts/docker-run.sh" ]
