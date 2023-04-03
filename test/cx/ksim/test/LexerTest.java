package cx.ksim.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import cx.ksim.mather.Lexer;
import cx.ksim.mather.TokenKind;
import cx.ksim.mather.UnknowCharacterException;

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
		assertThatThrownBy(() -> {
			new Lexer("1x1");
		}).isInstanceOf(UnknowCharacterException.class)
		.hasMessage("Unknown function call x");
	}
	
	@Test
	void itShouldLexeFactToken() {
		Lexer lexer = new Lexer("6!");
	}
	
	@Test
	void itShouldProduceAFLoat() {
		Lexer lexer = new Lexer("1.");
		assertThat(lexer.peek().get().kind()).isEqualTo(TokenKind.NUMBER);
		lexer = new Lexer(".1");
		assertThat(lexer.peek().get().kind()).isEqualTo(TokenKind.NUMBER);
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
