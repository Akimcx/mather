package cx.ksim.mather;

enum TokenKind {
	INTEGER,FLOAT,
	PLUS,MINUS,
	DIV,MULT
}


record Token(TokenKind kind, String value) {}
