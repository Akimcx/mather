package cx.ksim.mather;

public class Parser {

	private static Lexer lexer;

	public static Expression parse(String expr) {
		lexer = new Lexer(expr);
		Expression a = parseExpression();
		if (lexer.hasNext()) {
			throw new IllegalTokenException(String.format("Unexpected token %s", lexer.next()));
		}
		return a;
	}

	// Expression = [ "-" | "+" ] Term { "-" | "+" Term };
	private static Expression parseExpression() {
		Expression a = null;
		Token token = lexer.next();
		if (token.kind() == TokenKind.MINUS) {
			a = parseTerm(lexer.next());
			a = new UnaryExpression(a, "-");
		} else if (token.kind() == TokenKind.PLUS) {
			a = parseTerm(lexer.next());
		} else {
			a = parseTerm(token);
		}

		while (lexer.hasNext()) {
			token = lexer.peek().get();
			if (token.kind() == TokenKind.PLUS) {
				lexer.next();
				Expression b = parseTerm(lexer.next());
				a = new BinaryExpression(a, b, "+");
			} else if (token.kind() == TokenKind.MINUS) {
				lexer.next();
				Expression b = parseTerm(lexer.next());
				a = new BinaryExpression(a, b, "-");
			} else {
				return a;
			}
		}
		return a;
	}

	// Term = Factor { "*" | "/" Factor };
	private static Expression parseTerm(Token t) {
		Expression a = parseExponent(t);

		while (lexer.hasNext()) {
			Token token = lexer.peek().get();
			if (token.kind() == TokenKind.MULT) {
				lexer.next();
				Expression b = parseExponent(lexer.next());
				a = new BinaryExpression(a, b, "*");
			} else if (token.kind() == TokenKind.DIV) {
				lexer.next();
				Expression b = parseExponent(lexer.next());
				a = new BinaryExpression(a, b, "/");
			} else {
				return a;
			}
		}

		return a;
	}

	private static Expression parseExponent(Token t) {
		Expression a = parseFactor(t);

		while (lexer.hasNext()) {
			Token token = lexer.peek().get();
			if (token.kind() == TokenKind.EXPONENT) {
				lexer.next();
				Expression b = parseFactor(lexer.next());
				a = new BinaryExpression(a, b, "^");
			} else {
				return a;
			}
		}

		return a;
	}

	// Factor = ( Number | "(" Expression ")"
	// | Function_Name, "(" Expression ")" ) [ Unary_OP ];
	private static Expression parseFactor(Token t) {
		Expression a;
		switch (t.kind()) {
			case NUMBER -> a = new LiteralNode(Double.parseDouble(t.value()));
			case OPEN_PAREN -> {
				a = parseExpression();
				expect(TokenKind.CLOSE_PAREN);
				lexer.next();
			}
			case FUNC_CALL -> {
				expect(TokenKind.OPEN_PAREN);
				lexer.next();
				a = new UnaryExpression(parseExpression(), t.value());
				expect(TokenKind.CLOSE_PAREN);
				lexer.next();
			}
			default -> throw new IllegalTokenException(
					String.format("You cannot have the token [%s] here", t));
		}
		if (lexer.hasNext()) {
			t = lexer.peek().get();
			if (t.kind() == TokenKind.UNARY_OP) {
				a = new UnaryExpression(a, t.value());
				lexer.next();
			}
		}
		return a;

	}

	private static void expect(TokenKind kind) {
		if (lexer.peek().get().kind() != kind) {
			throw new IllegalTokenException(
					String.format("Expected %s but got %s\n", kind, lexer.peek().get()));
		}
	}

	public static void main(String[] args) {
		for (String arg : args) {
			var expr = Parser.parse(arg);
			System.out.printf("%s = %s\n", arg, expr.eval());
		}
	}
}
