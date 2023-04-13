# Mather

This program evaluates standard mathematical expressions. The expressions can use real numbers and support the binary operations

- Plus
- Minus
- Multiply
- Divide

and the unary operations / function call

- Negate
- Factorial

The expressions are defined by the EBNF rules:

```EBNF
Expression = [ "-" | "+" ] Term { "-" | "+" Term };
Term = Factor { "*" | "/" Factor };
Factor = ( Number | "(" Expression ")"
        | Function_Name, "(" Expression ")" ) [ Unary_OP ];
Unary_OP = "!" | "%";
Function_Name = "sin" | "cos" | "tan" | "log" | "ln";
Number = Integer | Float;
Float = Integer, ".", Integer;
Integer = Digit { Digit };
Digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" ;
```

## Quick Start

```console
./compile.sh
./run.sh
```
## Issues
- Better Error Handeling
- Degree & Radian Mode
- AST's view & modification
- Support for % operation
<!-- TODO -->
# Build the project
## Issues
- Better Error Handeling
- Degree & Radian Mode
- AST's view & modification
- Support for % operation
