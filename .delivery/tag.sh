#!/bin/bash

VERSION=$(cat version.txt)

if [[ "$VERSION" == *"-SNAPSHOT"* ]]
then
  sed s/-SNAPSHOT/-build-"$GITHUB_RUN_NUMBER"/ version.txt > tag.txt
else
  echo "$VERSION" > tag.txt
fi