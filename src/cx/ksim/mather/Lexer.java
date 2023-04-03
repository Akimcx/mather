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
			char _char = expr.charAt( i );
			if ( Character.isWhitespace( _char ) )
				continue;

			if ( Character.isDigit( _char ) || _char == '.' ) {
				boolean isFloat = _char == '.';
				String num = Character.toString( _char );
				i++;
				while ( i < len && (Character.isDigit( _char = expr.charAt( i ) ) || _char == '.') ) {
					if ( _char == '.' ) {
						if ( isFloat ) {
							throw new IllegalCharacterException(
									String.format( "You cannot have a [%s] here", _char ) );
						} else {
							isFloat = true;
						}
					}
					num += Character.toString( _char );
					i++;
				}
				tokens.add( new Token( TokenKind.NUMBER, num ) );
				i--;
			} else if ( _char >= 'a' && _char <= 'z' ) {
				String funcCall = _char + "";
				i++;
				while ( i < len && ((_char = expr.charAt( i )) >= 'a') && (_char <= 'z') ) {
					funcCall += _char + "";
					i++;
				}
				i--;
				switch ( funcCall ) {
					case "sin", "cos", "tang", "log", "ln" -> tokens.add( new Token( TokenKind.FUNC_CALL, funcCall ) );
					default ->
						throw new UnknowCharacterException( String.format( "Unknown function call %s", funcCall ) );
				}
			} else if ( _char == '+' ) {
				tokens.add( new Token( TokenKind.PLUS, Character.toString( _char ) ) );
			} else if ( _char == '-' ) {
				tokens.add( new Token( TokenKind.MINUS, Character.toString( _char ) ) );
			} else if ( _char == '*' ) {
				tokens.add( new Token( TokenKind.MULT, Character.toString( _char ) ) );
			} else if ( _char == '/' ) {
				tokens.add( new Token( TokenKind.DIV, Character.toString( _char ) ) );
			} else if ( _char == '(' ) {
				tokens.add( new Token( TokenKind.OPEN_PAREN, Character.toString( _char ) ) );
			} else if ( _char == ')' ) {
				tokens.add( new Token( TokenKind.CLOSE_PAREN, Character.toString( _char ) ) );
			} else if ( _char == '!' ) {
				tokens.add( new Token( TokenKind.UNARY_OP, Character.toString( _char ) ) );
			} else if ( _char == '%' ) {
				tokens.add( new Token( TokenKind.UNARY_OP, Character.toString( _char ) ) );
			} else if ( _char == '^' ) {
				tokens.add( new Token( TokenKind.EXPONENT, Character.toString( _char ) ) );
			} else {
				throw new UnknowCharacterException( String.format( "Unknown character [%s] found\n", _char ) );
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
			throw new NoSuchElementException();
		cursor += 1;
		return this.iterator.next();
	}

	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

}
