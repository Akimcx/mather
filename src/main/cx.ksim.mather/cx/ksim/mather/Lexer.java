package cx.ksim.mather;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Lexer implements Iterator<Token> {

	private List<Token> tokens;
	private Iterator<Token> iterator;
	private int cursor = 0;

	public Lexer(String expr) {
		this.tokens = new ArrayList<>();
		tokenize( expr );
		this.iterator = tokens.iterator();
	}

	private void tokenize(String expr) {
		for ( int i = 0, len = expr.length(); i < len; i++ ) {
			char currentChar = expr.charAt( i );
			if ( Character.isWhitespace( currentChar ) )
				continue;

			if ( Character.isDigit( currentChar ) || currentChar == '.' ) {
				boolean isFloat = currentChar == '.';

				String num = Character.toString( currentChar );
				i++;
				while ( i < len && (Character.isDigit( currentChar = expr.charAt( i ) ) || currentChar == '.') ) {
					if ( currentChar == '.' ) {
						if ( isFloat ) {
							throw new NumberFormatException();
						}
						isFloat = true;
					}
					num += Character.toString( currentChar );
					i++;
				}
				tokens.add( new Token( TokenKind.NUMBER, num ) );
				i--;
			} else if ( currentChar >= 'a' && currentChar <= 'z' ) {
				String funcCall = currentChar + "";
				i++;
				while ( i < len && ((currentChar = expr.charAt( i )) >= 'a') && (currentChar <= 'z') ) {
					funcCall += currentChar + "";
					i++;
				}
				i--;
				switch ( funcCall ) {
					case "sin", "cos", "tang", "log", "ln" -> tokens.add( new Token( TokenKind.FUNC_CALL, funcCall ) );
					default -> {
//						throw new UnknowCharacterException(
						System.err.println( String.format(
								"Unknown function call %s: %s\n"
										+ " ".repeat( i + "Unknown function call : ".length() + 1 ) + "^",
								funcCall, expr ) );
					}
				}
			} else if ( currentChar == '+' ) {
				tokens.add( new Token( TokenKind.PLUS, Character.toString( currentChar ) ) );
			} else if ( currentChar == '-' ) {
				tokens.add( new Token( TokenKind.MINUS, Character.toString( currentChar ) ) );
			} else if ( currentChar == '*' ) {
				tokens.add( new Token( TokenKind.MULT, Character.toString( currentChar ) ) );
			} else if ( currentChar == '/' ) {
				tokens.add( new Token( TokenKind.DIV, Character.toString( currentChar ) ) );
			} else if ( currentChar == '(' ) {
				tokens.add( new Token( TokenKind.OPEN_PAREN, Character.toString( currentChar ) ) );
			} else if ( currentChar == ')' ) {
				tokens.add( new Token( TokenKind.CLOSE_PAREN, Character.toString( currentChar ) ) );
			} else if ( currentChar == '!' ) {
				tokens.add( new Token( TokenKind.UNARY_OP, Character.toString( currentChar ) ) );
			} else if ( currentChar == '%' ) {
				tokens.add( new Token( TokenKind.UNARY_OP, Character.toString( currentChar ) ) );
			} else if ( currentChar == '^' ) {
				tokens.add( new Token( TokenKind.EXPONENT, Character.toString( currentChar ) ) );
			} else {
				throw new UnknowCharacterException( String.format( "Unknown character [%s] found\n", currentChar ) );
			}
		}
	}

	public void dump() {
		System.out.println( tokens );
	}

	public Optional<Token> peek() {
		if ( !hasNext() )
			return Optional.empty();
		return Optional.of( tokens.get( cursor ) );
	}

	public Token next() {
		if ( !hasNext() )
			throw new NoSuchElementException( "You've reached End Of Token Stream" );
		cursor += 1;
		return this.iterator.next();
	}

	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

}
