#!/bin/bash

set -e

apt update
apt install -y curl

# install Node

curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.4/install.sh | bash

source "$HOME/.nvm/nvm.sh"

nvm install 24.15.0

npm -g install serve

# build and launch auto-converter frontend

cd frontend

npm install
npm run build
npm run serve
