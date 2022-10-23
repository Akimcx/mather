package cx.ksim.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import cx.ksim.mather.Lexer;

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

}
