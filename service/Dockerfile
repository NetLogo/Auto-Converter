FROM ubuntu:latest

COPY package.json tsconfig.json /service/
COPY scripts /service/scripts
COPY src /service/src

RUN apt update && apt install -y curl zip unzip

SHELL [ "/bin/bash", "-c" ]

RUN curl -s "https://get.sdkman.io?ci=true" | bash && \
    source "/root/.sdkman/bin/sdkman-init.sh" && \
    sdk install java 6.0.119-zulu && \
    sdk install java 17.0.19-zulu && \
    sdk install scala 3.7.0

ENV JAVA6="/root/.sdkman/candidates/java/6.0.119-zulu/bin/java"
ENV JAVA17="/root/.sdkman/candidates/java/17.0.19-zulu/bin/java"
ENV SCALA3="/root/.sdkman/candidates/scala/3.7.0/bin/scalac"

RUN curl -s "https://scala-lang.org/files/archive/scala-2.9.2.deb" -o scala-2.9.2.deb && \
    apt install -y ./scala-2.9.2.deb

ENV SCALA2=/usr/bin/scalac

ENV NVM_DIR=/usr/local/nvm
ENV NODE_VERSION=24.15.0

RUN mkdir -p "$NVM_DIR"

RUN curl -o- "https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.4/install.sh" | bash && \
    source "$NVM_DIR/nvm.sh" && \
    nvm install "$NODE_VERSION" && \
    npm -g install serve

ENV PATH="$PATH:$NVM_DIR/versions/node/v$NODE_VERSION/bin"

RUN cd service && \
    npm install && \
    npm run build

EXPOSE 4242

ENTRYPOINT [ "/service/scripts/docker-run.sh" ]
