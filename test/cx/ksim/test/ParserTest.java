package cx.ksim.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import cx.ksim.mather.Node;
import cx.ksim.mather.Parser;

class ParserTest {
	
	@Test
	void itshouldReturn1() {
		Parser parser = new Parser();
		Node a = parser.parse("1");
		assertThat(a.print()).isEqualTo("1.0");
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

}
