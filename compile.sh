#!/usr/bin/env bash

set -x

rm -rf bin
javac -d bin/main -cp ./lib/picocli-4.7.5.jar $(find src/main -name "*.java")

if [ "$1" = "-t" ]; then
    javac -d bin/main -cp bin/main:./lib/junit-platform-console-standalone-1.10.0.jar $(find src/test -name "*.java")
fi
# javac -d bin/test -cp bin/main/cx.ksim.mather.cli/ src/test/cx.ksim.mather.cli/cx/ksim/mather/test/LexerTest.java src/test/cx.ksim.mather.cli/cx/ksim/mather/test/ParserTest.java
