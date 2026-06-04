#!/bin/bash

set -e

docker build -t auto-converter --platform linux/amd64 .
docker run -p 4242:4242 auto-converter
