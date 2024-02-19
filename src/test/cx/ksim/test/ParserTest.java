package cx.ksim.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import cx.ksim.mather.IllegalTokenException;
import cx.ksim.mather.Expression;
import cx.ksim.mather.Parser;
import cx.ksim.mather.Token;
import cx.ksim.mather.TokenKind;

class ParserTest {

	@Test
	void itshouldPrint0() {
		Parser parser = new Parser();
		Expression a = parser.parse( "0" );
		assertThat( a.eval() ).isEqualTo( 0.0 );
		a = parser.parse( "1+1-2" );
		assertThat( a.eval() ).isEqualTo( 0.0 );
		a = parser.parse( "log(1)" );
		assertThat( a.eval() ).isEqualTo( 0.0 );
		a = parser.parse( "ln(1)" );
		assertThat( a.eval() ).isEqualTo( 0.0 );
	}

	@Test
	void itshouldPrint1() {
		Parser parser = new Parser();
		Expression a = parser.parse( "1" );
		assertThat( a.eval() ).isEqualTo( 1.0 );
		a = parser.parse( "1+1-1" );
		assertThat( a.eval() ).isEqualTo( 1.0 );
		a = parser.parse( "log(10)" );
		assertThat( a.eval() ).isEqualTo( 1.0 );
		a = parser.parse( "+1" );
		assertThat( a.eval() ).isEqualTo( 1.0 );
	}

	@Test
	void itshouldthrowanexception() {
		Parser parser = new Parser();
		assertThatThrownBy( () -> {
			parser.parse( "(34+35)(2)" );
		} ).isInstanceOf( IllegalTokenException.class )
				.hasMessage( "Unexpected token " + new Token( TokenKind.OPEN_PAREN, "(" ) );
	}

	@Test
	void itshouldPrint2() {
		Parser parser = new Parser();
		Expression a = parser.parse( "1+1" );
		assertThat( a.eval() ).isEqualTo( 2.0 );
		a = parser.parse( "2^1" );
		assertThat( a.eval() ).isEqualTo( 2.0 );
	}

	@Test
	void itshouldPrintNegative1() {
		Parser parser = new Parser();
		Expression a = parser.parse( "-1" );
		assertThat( a.eval() ).isEqualTo( -1.0 );
		a = parser.parse( "1*(-1)" );
		assertThat( a.eval() ).isEqualTo( -1.0 );
		a = parser.parse( "5-6" );
		assertThat( a.eval() ).isEqualTo( -1.0 );
	}

	@Test
	void itshouldPrint13() {
		Parser parser = new Parser();
		Expression a = parser.parse( "+13" );
		assertThat( a.eval() ).isEqualTo( 13.0 );
	}

	@Test
	void itshouldPrint8() {
		Parser parser = new Parser();
		Expression a = parser.parse( "2*3+2" );
		assertThat( a.eval() ).isEqualTo( 8.0 );
		a = parser.parse( "2^3" );
		assertThat( a.eval() ).isEqualTo( 8.0 );
	}

	@Test
	void itshouldPrint7() {
		Parser parser = new Parser();
		Expression a = parser.parse( "2*3+2/2" );
		assertThat( a.eval() ).isEqualTo( 7.0 );
		a = parser.parse( "4+3*(8-2*(6-3))/2" );
		assertThat( a.eval() ).isEqualTo( 7.0 );
	}

	@Test
	void itshouldPrintNegative8() {
		Parser parser = new Parser();
		Expression a = parser.parse( "2-(3+7)" );
		assertThat( a.eval() ).isEqualTo( -8.0 );
	}

	@Test
	void itshouldPrint6() {
		Parser parser = new Parser();
		Expression a = parser.parse( "2-3+7" );
		assertThat( a.eval() ).isEqualTo( 6.0 );
		a = parser.parse( "3-3+6" );
		assertThat( a.eval() ).isEqualTo( 6.0 );
		a = parser.parse( "45/5-3" );
		assertThat( a.eval() ).isEqualTo( 6.0 );
	}

	@Test
	void itshouldPrint21() {
		Parser parser = new Parser();
		Expression a = parser.parse( "9/3*7" );
		assertThat( a.eval() ).isEqualTo( 21.0 );
	}

	@Test
	void itshouldCalcSinOf90() {
		Parser parser = new Parser();
		Expression a = parser.parse( "sin(90)" );
		assertThat( a.eval() ).isEqualTo( Math.sin( 90 ) );
	}

	@Test
	void itshouldCalcFactOf6() {
		Parser parser = new Parser();
		Expression a = parser.parse( "6!" );
		assertThat( a.eval() ).isEqualTo( 720.0 );
	}

	@Test
	void itshouldCalcCosOf90() {
		Parser parser = new Parser();
		Expression a = parser.parse( "cos(90)" );
		assertThat( a.eval() ).isEqualTo( Math.cos( 90 ) );
	}

	@Test
	void itshouldCalcTangOf90() {
		Parser parser = new Parser();
		Expression a = parser.parse( "tang(90)" );
		assertThat( a.eval() ).isEqualTo( Math.tan( 90 ) );
	}

	@Test
	void itShouldCalcSin90ThanTimes2() {
		Parser parser = new Parser();
		Expression a = parser.parse( "sin(90) * 2" );
		assertThat( a.eval() ).isEqualTo( Math.sin( 90 ) * 2 );
	}

	@Test
	void itshouldPrint3() {
		Parser parser = new Parser();
		Expression a = parser.parse( "63/  ((3   *7)        )" );
		assertThat( a.eval() ).isEqualTo( 3.0 );
	}

	@Test
	void itshouldPrint22() {
		Parser parser = new Parser();
		Expression a = parser.parse( "42/7*4-9+7" );
		assertThat( a.eval() ).isEqualTo( 22.0 );
	}

	@Test
	void itshouldPrint27() {
		Parser parser = new Parser();
		Expression a = parser.parse( "(4*3/6+1)*3^2" );
		assertThat( a.eval() ).isEqualTo( 27.0 );
	}

	@Test
	void itshouldPrint83() {
		Parser parser = new Parser();
		Expression a = parser.parse( "60/5*7-2+1" );
		assertThat( a.eval() ).isEqualTo( 83.0 );
	}

	@Test
	void itshouldPrint2490() {
		Parser parser = new Parser();
		Expression a = parser.parse( "(450*3)+(570*2)" );
		assertThat( a.eval() ).isEqualTo( 2490.0 );
	}

	@Test
	void itshouldPrint50Percent() {
		Parser parser = new Parser();
		Expression a = parser.parse( "50%" );
		assertThat( a.eval() ).isEqualTo( 0.5 );
	}

	@Test
	void itshouldPrint75() {
		Parser parser = new Parser();
		Expression a = parser.parse( "50+50%" );
		assertThat( a.eval() ).isEqualTo( 75.0 );
	}

	@Test
	void itshouldPrint130() {
		Parser parser = new Parser();
		Expression a = parser.parse( "100+30%" );
		assertThat( a.eval() ).isEqualTo( 130.0 );
	}

	@Test
	void itshouldPrint536Point75() {
		Parser parser = new Parser();
		Expression a = parser.parse( "475+13%" );
		assertThat( a.eval() ).isEqualTo( 536.75 );
	}

	@Test
	void itshouldPrintPoint75() {
		Parser parser = new Parser();
		Expression a = parser.parse( "75%" );
		assertThat( a.eval() ).isEqualTo( .75 );
	}

	@Test
	void itshouldPrint9() {
		Parser parser = new Parser();
		Expression a = parser.parse( "3^2" );
		assertThat( a.eval() ).isEqualTo( 9 );
	}

}
