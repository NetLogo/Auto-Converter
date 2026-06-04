#!/bin/bash

set -e

apt update
apt install -y curl zip unzip

curl -s "https://get.sdkman.io?ci=true" | bash

source "$HOME/.sdkman/bin/sdkman-init.sh"

# install Java 6

sdk install java 6.0.119-zulu

export JAVA6="$HOME/.sdkman/candidates/java/6.0.119-zulu/bin/java"

# install Java 17

sdk install java 17.0.19-zulu

export JAVA17="$HOME/.sdkman/candidates/java/17.0.19-zulu/bin/java"

# install Scala 2

curl -s https://scala-lang.org/files/archive/scala-2.9.2.deb -o scala-2.9.2.deb

apt install -y ./scala-2.9.2.deb

export SCALA2=/usr/bin/scalac

# install Scala 3

sdk install scala 3.7.0

export SCALA3="$HOME/.sdkman/candidates/scala/3.7.0/bin/scalac"

# install Node

curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.4/install.sh | bash

source "$HOME/.nvm/nvm.sh"

nvm install 24.15.0

# build and launch auto-converter service

cd service

npm install
npm run build
npm run serve
