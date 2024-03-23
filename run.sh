#!/usr/bin/env bash

set -x

if [ "$1" = "-t" ]; then
    java -jar ./lib/junit-platform-console-standalone-1.10.0.jar -cp bin/main --select-class "cx.ksim.mather.cli.${2}"
else
    java -cp bin/main:./lib/picocli-4.7.5.jar cx.ksim.mather.cli.Parser "$@"
fi
