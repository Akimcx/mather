#/usr/bin/env bash

cli_module=cx.ksim.mather.cli

if [[ -n $1 ]] && [[ $1 = 'test' ]]; then
    java --module-path bin/main --module $cli_module/$cli_module.Parser $@
else
    java --module-path bin/main:$PICOCLI_PATH --module $cli_module/$cli_module.Parser $@
fi
