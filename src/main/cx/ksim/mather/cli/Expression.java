package cx.ksim.mather.cli;

public sealed interface Expression permits BinaryExpression, LiteralNode, UnaryExpression {
    abstract double eval();

    abstract String print();

    abstract String sexp();
}
