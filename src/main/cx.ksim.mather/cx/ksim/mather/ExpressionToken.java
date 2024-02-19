package cx.ksim.mather;

public enum ExpressionToken implements TokenKind {
	PLUS("+"),MINUS("-");

	private final String operator;

	ExpressionToken(String operator) {
		this.operator = operator;	
	}
	public String operator() {
		return this.operator;
	}
}

