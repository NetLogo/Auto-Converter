#!/bin/bash

set -e

export VITE_AC_SERVICE_HOST=$AC_SERVICE_HOST

cd frontend

npm run build
npm run serve
