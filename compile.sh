#/usr/bin/env bash

rm -rf bin
javac -d bin/main $(find src/main -name "*.java")
# javac -d bin/test -cp bin/main/cx.ksim.mather.cli/ src/test/cx.ksim.mather.cli/cx/ksim/mather/test/LexerTest.java src/test/cx.ksim.mather.cli/cx/ksim/mather/test/ParserTest.java
