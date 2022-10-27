package cx.ksim.mather;

public class Parser {

	private static Lexer lexer;

	public Node parse(String expr) {
		lexer = new Lexer(expr);
		return parseExpression();
	}

	//		Expression = [ "-" | "+" ] Term { "-" | "+" Term };
	private Node parseExpression() {
		Node a = null;
		Token token = lexer.peek().orElseThrow(
				() -> new IllegalTokenException("No token provided"));
		if(token.kind() == TokenKind.MINUS) {
			lexer.next();
			a = parseTerm();
			a = new UnaryExpression(a, "-");
		} else if(token.kind() == TokenKind.PLUS) {
			lexer.next();
			a = parseTerm();					
		} else {
			a = parseTerm();
		}

		while(lexer.peek().isPresent()) {
			token = lexer.peek().get();
			if(token.kind() == TokenKind.PLUS) {
				lexer.next();
				Node b = parseTerm();
				a = new BinaryExpression(a, b, "+");
			} else if(token.kind() == TokenKind.MINUS) {
				lexer.next();
				Node b = parseTerm();
				a = new BinaryExpression(a, b, "-");
			} else {
				return a;					
			}
		}
		return a;
	}

	//		Term = Factor { "*" | "/" Factor };
	private Node parseTerm() {
		Node a = parseFactor();

		while(lexer.peek().isPresent()) {
			Token token = lexer.peek().get();
			if(token.kind() == TokenKind.MULT) {
				lexer.next();
				Node b = parseFactor();
				a = new BinaryExpression(a, b, "*");
			} else if(token.kind() == TokenKind.DIV) {
				lexer.next();
				Node b = parseFactor();
				a = new BinaryExpression(a, b, "/");
			} else {
				return a;
			}
		}
		return a;
	}

	//		Factor = ( Number | "(" Expression ")"
	//		        | Function_Name, "(" Expression ")" ) [ Unary_OP ];
	private Node parseFactor() {
		Token token = lexer.peek().orElseThrow(
				() -> new IllegalTokenException("No token provided"));

		if(token.kind() == TokenKind.INTEGER || token.kind() == TokenKind.FLOAT) {
			lexer.next();
			return new LiteralNode(Double.parseDouble(token.value()));
		}
		
		if(token.kind() == TokenKind.OPEN_PAREN) {
			lexer.next();
			Node a = parseExpression();
			if(lexer.peek().get().kind() != TokenKind.CLOSE_PAREN) {
				throw new IllegalTokenException(
						String.format("Expected %s but got %s\n",
								TokenKind.CLOSE_PAREN, lexer.peek().get()));
			}
			lexer.next();
			return a;
		}

		throw new IllegalTokenException(
				String.format("You cannot have the token [%s] here", token));
	}

	public static void main(String[] args) {
		Parser parser = new Parser();
		for( String arg : args) {
			System.out.printf("%s -> %s\n", arg, parser.parse(arg));
		}
	}
}
