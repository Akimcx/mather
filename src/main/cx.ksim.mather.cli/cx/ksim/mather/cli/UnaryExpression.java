package cx.ksim.mather.cli;

public class UnaryExpression implements Expression {

	private Expression node;
	private String operator;

	// TODO: Add a way to change from radians to degrees
	public UnaryExpression(Expression node, String operator) {
		this.node = node;
		this.operator = operator;
	}

	private double fact(double v) {
		// TODO: Add support for float number
		if ( v == 1 || v == 0 )
			return 1;
		return v * fact( v - 1 );
	}

	@Override
	public double eval() {
		return switch ( operator ) {
			case "+" -> node.eval();
			case "-" -> -node.eval();
			case "!" -> fact( node.eval() );
			case "%" -> (node.eval() / 100);
			case "sin" -> Math.sin( node.eval() );
			case "cos" -> Math.cos( node.eval() );
			case "tang" -> Math.tan( node.eval() );
			case "log" -> Math.log10( node.eval() );
			case "ln" -> Math.log( node.eval() );
			default -> throw new IllegalArgumentException( "Unexpected value: " + operator );
		};
	}

	public String getOperator() {
		return operator;
	}

	@Override
	public String print() {
		return switch ( operator ) {
			case "+" -> node.print();
			case "-" -> String.format( "(-%s)", node.print() );
			case "!" -> String.format( "fact(%s)", node.print() );
			case "sin" -> String.format( "sin(%s)", node.print() );
			case "cos" -> String.format( "con(%s)", node.print() );
			case "tang" -> String.format( "tang(%s)", node.print() );
			case "%" -> String.format( "(%s)", node.print() );
			default -> throw new IllegalArgumentException( "Unexpected value: " + operator );
		};
	}

	@Override
	public String toString() {
		return print();
	}

}
