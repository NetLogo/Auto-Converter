FROM ubuntu:latest

COPY package.json tsconfig.json tsconfig.app.json tsconfig.node.json vite.config.ts index.html env.d.ts /frontend/
COPY scripts /frontend/scripts
COPY src /frontend/src
COPY public /frontend/public

ENTRYPOINT [ "/frontend/scripts/docker-run.sh" ]
