package cx.ksim.mather.cli;

public sealed interface Expression permits BinaryExpression, LiteralExpression, UnaryExpression {
	abstract double eval();
	abstract String print();
}
