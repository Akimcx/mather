package cx.ksim.mather.cli;

public enum TermToken implements TokenKind {
	DIV("/"),MULT("*");

	private String value;

	TermToken(String value) {
		this.value = value;
	}

	public String operator() {
		return value;
	}
}

