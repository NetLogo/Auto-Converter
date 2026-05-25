#!/bin/bash

set -e

if [[ -n SCALA3 ]]; then
  echo "Error: You must set the environment variable 'SCALA3'."
  exit 1
fi

rm -rf out/lib/7.0.4
mkdir -p out/lib/7.0.4

OPTS="-unchecked -deprecation -Werror -Wunused:linted"
CP="releases/7.0.4/*.jar"

$SCALA3 $OPTS -cp $CP -d out/lib/7.0.4/AutoConverter.jar src/scala/7.0.4/AutoConverter.scala

cp -r releases/7.0.4/* out/lib/7.0.4
