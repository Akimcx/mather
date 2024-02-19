#/usr/bin/env bash

rm -rf bin
javac -d bin/main --module cx.ksim.mather --module-source-path src/main/
