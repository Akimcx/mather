#/usr/bin/env bash

cli_module=cx.ksim.mather.cli
test_module=cx.ksim.mather.test

if [ $1 = 'test' ]; then
    java --module-path bin/main --module $test_module/$test_module.Parser $@
else
    java --module-path bin/main --module $cli_module/$cli_module.Parser $@
fi
