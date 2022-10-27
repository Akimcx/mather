package cx.ksim.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import cx.ksim.mather.Lexer;
import cx.ksim.mather.TokenKind;

class LexerTest {

	@Test
	void itShouldReturnAnEmptyOptinal() {
		Lexer lexer = new Lexer("1+1");
		lexer.next();
		lexer.next();
		lexer.next();
		assertThat(lexer.peek()).isEmpty();
	}
	
	@Test
	void itShouldThrowAnUnknowCharacterException() {
		assertThat(new Lexer("1x1"));
	}
	
	@Test
	void itShouldProduceAFLoat() {
		Lexer lexer = new Lexer("1.");
		assertThat(lexer.peek().get().kind()).isEqualTo(TokenKind.FLOAT);
		lexer = new Lexer(".1");
		assertThat(lexer.peek().get().kind()).isEqualTo(TokenKind.FLOAT);
	}
	
	@Test
	void itShouldProduceAnOpenParen() {
		Lexer lexer = new Lexer("(");
		assertThat(lexer.peek().get().kind()).isEqualTo(TokenKind.OPEN_PAREN);
	}
	
	@Test
	void itShouldProduceACloseParen() {
		Lexer lexer = new Lexer(")");
		assertThat(lexer.peek().get().kind()).isEqualTo(TokenKind.CLOSE_PAREN);
	}

}
