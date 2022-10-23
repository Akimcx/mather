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

}
