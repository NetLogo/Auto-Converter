#!/bin/bash

set -e

if [[ ! -v JAVA17 ]]; then
  echo "Error: You must set the environment variable 'JAVA17'."
  exit 1
fi

if [[ ! -v SCALA3 ]]; then
  echo "Error: You must set the environment variable 'SCALA3'."
  exit 1
fi

export JAVACMD=$JAVA17

rm -rf out/lib/7.0.4
mkdir -p out/lib/7.0.4

if [[ ! -d releases/7.0.4 ]]; then
  mkdir -p releases/7.0.4

  curl -L https://ccl-artifacts.s3.us-east-1.amazonaws.com/autoconverter-bundles/netlogo-7.0.4.zip -o /tmp/netlogo-7.0.4.zip
  unzip /tmp/netlogo-7.0.4.zip -d releases/7.0.4

  rm -rf /tmp/netlogo-7.0.4.zip
fi

cp -r releases/7.0.4/* out/lib/7.0.4

OPTS="-unchecked -deprecation -Werror -Wunused:linted"
CP="releases/7.0.4/*.jar"

$SCALA3 $OPTS -cp $CP -d out/lib/7.0.4/AutoConverter.jar src/scala/7.0.4/AutoConverter.scala
