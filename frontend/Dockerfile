FROM ubuntu:latest

COPY package.json tsconfig.json tsconfig.app.json tsconfig.node.json vite.config.ts index.html env.d.ts /frontend/
COPY scripts /frontend/scripts
COPY src /frontend/src
COPY public /frontend/public

RUN apt update && apt install -y curl

ENV NVM_DIR=/usr/local/nvm
ENV NODE_VERSION=24.15.0

RUN mkdir -p "$NVM_DIR"

SHELL [ "/bin/bash", "-c" ]

RUN curl -o- "https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.4/install.sh" | bash && \
    source "$NVM_DIR/nvm.sh" && \
    nvm install "$NODE_VERSION" && \
    npm -g install serve

ENV PATH="$PATH:$NVM_DIR/versions/node/v$NODE_VERSION/bin"

RUN cd frontend && \
    npm install

EXPOSE 3000

ENTRYPOINT [ "/frontend/scripts/docker-run.sh" ]
