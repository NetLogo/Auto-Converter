#!/bin/bash

set -e

if [[ ! -v SCALA2 ]]; then
  echo "Error: You must set the environment variable 'SCALA2'."
  exit 1
fi

rm -rf out/lib/5.0.0
mkdir -p out/lib/5.0.0

OPTS="-unchecked -deprecation -Xfatal-warnings -Xlint"
CP="releases/5.0.0/NetLogo.jar:releases/5.0.0/scala-library.jar"

$SCALA2 $OPTS -cp $CP -d out/lib/5.0.0/AutoConverter.jar src/scala/5.0.0/AutoConverter.scala

cp -r releases/5.0.0/* out/lib/5.0.0
