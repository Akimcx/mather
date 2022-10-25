package cx.ksim.mather;

public class UnaryExpression implements Node{
	
	private Node node;
	private String operator;
	
	public UnaryExpression(Node node, String operator) {
		this.node = node;
		this.operator = operator;
	}

	@Override
	public double eval() {
		return switch(operator) {
			case "+" -> node.eval();
			case "-" -> -node.eval();
			default -> throw new IllegalArgumentException("Unexpected value: " + operator);
		};
	}

	@Override
	public String print() {
		return switch(operator) {
			case "+" -> node.print();
			case "-" -> String.format("(-%s)", node.print());
			default -> throw new IllegalArgumentException("Unexpected value: " + operator);
		};
	}
	
}
