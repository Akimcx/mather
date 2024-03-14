package cx.ksim.mather.cli;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "mather", version = "1.0.0", description = "Evaluate mathematical expression", mixinStandardHelpOptions = true)
public class Parser implements Callable<Integer> {

	@Parameters(index = "0..*", arity = "1..*", description = "The expressions to evaluate")
	String[] expressions;
	@Option(names = { "-p",
			"--print" }, description = "Pretty print the AST", negatable = true, defaultValue = "false")
	private boolean prettyPrint;

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
		Token token = lexer.next();
		Expression a = switch (token.kind()) {
			case ExpressionToken t when t == ExpressionToken.MINUS -> {
				Expression term = parseTerm(lexer.next());
				yield new UnaryExpression(term, t.operator());
			}
			case ExpressionToken t -> {
				yield parseTerm(lexer.next());
			}
			case TermToken t -> parseTerm(token);
			case FactorToken t -> parseTerm(token);
		};
		// if (token.kind() == TokenKind.MINUS) {
		// a = parseTerm(lexer.next());
		// a = new UnaryExpression(a, "-");
		// } else if (token.kind() == TokenKind.PLUS) {
		// a = parseTerm(lexer.next());
		// } else {
		// a = parseTerm(token);
		// }

		while (lexer.hasNext()) {
			token = lexer.peek().get();
			return switch (token.kind()) {
				case ExpressionToken t -> {
					lexer.next();
					Expression b = parseTerm(lexer.next());
					a = new BinaryExpression(a, b, t.operator());
					yield a;
				}
				case FactorToken t -> a;
				case TermToken t -> a;
			};
			// if (token.kind() == TokenKind.PLUS) {
			// lexer.next();
			// Expression b = parseTerm(lexer.next());
			// a = new BinaryExpression(a, b, "+");
			// } else if (token.kind() == TokenKind.MINUS) {
			// lexer.next();
			// Expression b = parseTerm(lexer.next());
			// a = new BinaryExpression(a, b, "-");
			// } else {
			// return a;
			// }
		}
		return a;
	}

	// Term = Factor { "*" | "/" Factor };
	private static Expression parseTerm(Token t) {
		Expression a = parseExponent(t);

		while (lexer.hasNext()) {
			Token token = lexer.peek().get();
			return switch (token.kind()) {
				case TermToken tk -> {
					lexer.next();
					Expression b = parseExponent(lexer.next());
					a = new BinaryExpression(a, b, tk.operator());
					yield a;
				}
				case ExpressionToken tk -> a;
				case FactorToken tk -> a;

			};
			// if (token.kind() == TokenKind.MULT) {
			// lexer.next();
			// Expression b = parseExponent(lexer.next());
			// a = new BinaryExpression(a, b, "*");
			// } else if (token.kind() == TokenKind.DIV) {
			// lexer.next();
			// Expression b = parseExponent(lexer.next());
			// a = new BinaryExpression(a, b, "/");
			// } else {
			// return a;
			// }
		}

		return a;
	}

	private static Expression parseExponent(Token t) {
		Expression a = parseFactor(t);

		// while (lexer.hasNext()) {
		// Token token = lexer.peek().get();

		// if (token.kind() == TokenKind.EXPONENT) {
		// lexer.next();
		// Expression b = parseFactor(lexer.next());
		// a = new BinaryExpression(a, b, "^");
		// } else {
		// return a;
		// }
		// }

		return a;
	}

	// Factor = ( Number | "(" Expression ")"
	// | Function_Name, "(" Expression ")" ) [ Unary_OP ];
	private static Expression parseFactor(Token t) {
		Expression a = null;
		if (!(t.kind() instanceof FactorToken factorToken)) {
			throw new IllegalArgumentException();
		}
		switch (factorToken) {
			case NUMBER -> a = new LiteralExpression(Double.parseDouble(t.value()));
			case OPEN_PAREN -> {
				a = parseExpression();
				expect(FactorToken.CLOSE_PAREN);
				lexer.next();
			}
			case FUNC_CALL -> {
				expect(FactorToken.OPEN_PAREN);
				lexer.next();
				a = new UnaryExpression(parseExpression(), t.value());
				expect(FactorToken.CLOSE_PAREN);
				lexer.next();
			}
			case CLOSE_PAREN, UNARY_OP ->
				throw new UnsupportedOperationException("Unimplemented case: " + factorToken);
		}
		if (lexer.hasNext()) {
			t = lexer.peek().get();
			if (t.kind() == FactorToken.UNARY_OP) {
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
		var exitCode = new CommandLine(new Parser()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public Integer call() throws Exception {
		for (String expression : expressions) {
			var expr = Parser.parse(expression);
			if (prettyPrint) {
				System.out.println(expr.print());
			} else {
				System.out.println(expr.eval());
			}
		}
		return 0;
	}
}
