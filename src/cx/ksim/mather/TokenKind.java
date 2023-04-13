package cx.ksim.mather;

public enum TokenKind {
	NUMBER(""), PLUS("+"), MINUS("-"), DIV("/"), MULT("*"), EXPONENT("^"), OPEN_PAREN("("), CLOSE_PAREN(")"),
	UNARY_OP(""), FUNC_CALL("");

	TokenKind(String value) {
	}
}