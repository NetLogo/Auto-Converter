#!/bin/bash

set -e

docker build -t auto-converter-fe .
docker run -p 3000:3000 auto-converter-fe
