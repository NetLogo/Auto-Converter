#!/bin/bash

set -e

if [[ ! -v JAVA6 ]]; then
  echo "Error: You must set the environment variable 'JAVA6'."
  exit 1
fi

if [[ ! -v SCALA2 ]]; then
  echo "Error: You must set the environment variable 'SCALA2'."
  exit 1
fi

export JAVACMD=$JAVA6

rm -rf out/lib/5.0.0
mkdir -p out/lib/5.0.0

if [[ ! -d releases/5.0.0 ]]; then
  mkdir -p releases/5.0.0

  curl -L https://ccl-artifacts.s3.us-east-1.amazonaws.com/autoconverter-bundles/netlogo-5.0.0.zip -o /tmp/netlogo-5.0.0.zip
  unzip /tmp/netlogo-5.0.0.zip -d releases/5.0.0

  rm -rf /tmp/netlogo-5.0.0.zip
fi

cp -r releases/5.0.0/* out/lib/5.0.0

OPTS="-unchecked -deprecation -Xfatal-warnings -Xlint"
CP="releases/5.0.0/NetLogo.jar:releases/5.0.0/scala-library.jar"

$SCALA2 $OPTS -cp $CP -d out/lib/5.0.0/AutoConverter.jar src/scala/5.0.0/*.scala
