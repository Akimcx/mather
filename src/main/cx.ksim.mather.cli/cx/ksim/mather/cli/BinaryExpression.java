package cx.ksim.mather.cli;

public final class BinaryExpression implements Expression {

	private Expression left;
	private Expression right;
	private String operator;

	public BinaryExpression(Expression left, Expression right, String operator) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}

	@Override
	public double eval() {
		return switch ( operator ) {
			case "+" -> {
				if ( right instanceof UnaryExpression r  &&  r.getOperator().equals( "%" ) ) {
					yield (left.eval() + (right.eval() * left.eval()));
				}
				yield (left.eval() + right.eval());
			}
			case "-" -> (left.eval() - right.eval());
			case "*" -> (left.eval() * right.eval());
			case "/" -> (left.eval() / right.eval());
			case "^" -> Math.pow( left.eval(), right.eval() );
			default -> throw new IllegalArgumentException( "Unexpected value: " + operator );
		};
	}

	@Override
	public String print() {
		return switch ( operator ) {
			case "+", "-", "*", "/", "^" -> String.format( "(%s %s %s)", left.print(), operator, right.print() );
			default -> throw new IllegalArgumentException( "Unexpected value: " + operator );
		};
	}

	@Override
	public String toString() {
		return print();
	}

	public String getAst() {
		return getAst( 0 );
	}

	private String getAst(int indentLevel) {
		String indent = "  ".repeat( indentLevel );
		System.out.println( indent + "BinaryExpression:" );
		System.out.println( indent + "  operator: " + operator );
		System.out.println( indent + "  left:" );
		if ( left instanceof BinaryExpression l) {
			l.getAst( indentLevel + 1 );
		} else {
			System.out.println( indent + "    " + left.print() );
		}
		System.out.println( indent + "  right:" );
		if ( right instanceof BinaryExpression r) {
			r.getAst( indentLevel + 1 );
		} else {
			System.out.println( indent + "    " + right.print() );
		}
		return indent;
	}

}
