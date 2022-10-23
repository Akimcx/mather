package cx.ksim.mather;

public class BinaryExpression implements Node {

	private Node left;
	private Node right;
	private String operator;
	public BinaryExpression(Node left, Node right, String operator) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}

	@Override
	public double eval() {
		return switch (operator) {
		case "+" -> (left.eval() + right.eval());
		case "-" -> (left.eval() - right.eval());
		case "*" -> (left.eval() * right.eval());
		case "/" -> (left.eval() / right.eval());
		case "^" -> Math.pow(left.eval(), right.eval());
		default -> throw new IllegalArgumentException("Unexpected value: " + operator);
		};
	}

	@Override
	public String print() {
		return switch (operator) {
		case "+" -> String.format("(%s %s %s)", left.print(), operator, right.print());
		case "-" -> String.format("(%s %s %s)", left.print(), operator, right.print());
		case "*" -> String.format("(%s %s %s)", left.print(), operator, right.print());
		case "/" -> String.format("(%s %s %s)", left.print(), operator, right.print());
		case "^" -> String.format("(%s %s %s)", left.print(), operator, right.print());
		default -> throw new IllegalArgumentException("Unexpected value: " + operator);
		};
	}

	@Override
	public String toString() {
		return print();
	}

}
