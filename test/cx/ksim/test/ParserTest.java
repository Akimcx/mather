package cx.ksim.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import cx.ksim.mather.IllegalTokenException;
import cx.ksim.mather.Node;
import cx.ksim.mather.Parser;
import cx.ksim.mather.Token;
import cx.ksim.mather.TokenKind;

class ParserTest {
	
	@Test
	void itshouldReturn1() {
		Parser parser = new Parser();
		Node a = parser.parse("1");
		assertThat(a.print()).isEqualTo("1.0");
	}
	
	@Test
	void itshouldthrowanexception() {
		Parser parser = new Parser();
		assertThatThrownBy(() -> {
			parser.parse("(34+35)(2)");
		}).isInstanceOf(IllegalTokenException.class)
		.hasMessage("Unhandle token " + new Token(TokenKind.OPEN_PAREN,"("));
	}
	
	@Test
	void itshouldPrint2() {
		Parser parser = new Parser();
		Node a = parser.parse("1+1");
		assertThat(a.eval()).isEqualTo(2.0);
	}
	
	@Test
	void itshouldPrintNegative1() {
		Parser parser = new Parser();
		Node a = parser.parse("-1");
		assertThat(a.eval()).isEqualTo(-1.0);
	}
	
	@Test
	void itshouldPrint13() {
		Parser parser = new Parser();
		Node a = parser.parse("+13");
		assertThat(a.eval()).isEqualTo(13.0);
	}
	
	@Test
	void itshouldPrint1() {
		Parser parser = new Parser();
		Node a = parser.parse("1+1-1");
		assertThat(a.eval()).isEqualTo(1.0);
	}
	
	@Test
	void itshouldPrint8() {
		Parser parser = new Parser();
		Node a = parser.parse("2*3+2");
		assertThat(a.eval()).isEqualTo(8.0);
	}
	
	@Test
	void itshouldPrint7() {
		Parser parser = new Parser();
		Node a = parser.parse("2*3+2/2");
		assertThat(a.eval()).isEqualTo(7.0);
	}
	
	@Test
	void itshouldPrintNegative8() {
		Parser parser = new Parser();
		Node a = parser.parse("2-(3+7)");
		assertThat(a.eval()).isEqualTo(-8.0);
	}
	
	@Test
	void itshouldPrint6() {
		Parser parser = new Parser();
		Node a = parser.parse("2-3+7");
		assertThat(a.eval()).isEqualTo(6.0);
	}
	
	@Test
	void itshouldPrint21() {
		Parser parser = new Parser();
		Node a = parser.parse("9/3*7");
		assertThat(a.eval()).isEqualTo(21.0);
	}
	
	@Test
	void itshouldCalcSinOf90() {
		Parser parser = new Parser();
		Node a = parser.parse("sin(90)");
		assertThat(a.eval()).isEqualTo(Math.sin(90));
	}
	
	@Test
	void itshouldCalcFactOf6() {
		Parser parser = new Parser();
		Node a = parser.parse("6!");
		assertThat(a.eval()).isEqualTo(720.0);
	}
	
	@Test
	void itshouldCalcCosOf90() {
		Parser parser = new Parser();
		Node a = parser.parse("cos(90)");
		assertThat(a.eval()).isEqualTo(Math.cos(90));
	}
	
	@Test
	void itshouldCalcTangOf90() {
		Parser parser = new Parser();
		Node a = parser.parse("tang(90)");
		assertThat(a.eval()).isEqualTo(Math.tan(90));
	}
	
	@Test
	void itShouldCalcSin90ThanTimes2() {
		Parser parser = new Parser();
		Node a = parser.parse("sin(90) * 2");
		assertThat(a.eval()).isEqualTo(Math.sin(90) * 2);
	}
	
	@Test
	void itshouldPrint3() {
		Parser parser = new Parser();
		Node a = parser.parse("63/((3*7)                     )");
		assertThat(a.eval()).isEqualTo(3.0);
	}

}
