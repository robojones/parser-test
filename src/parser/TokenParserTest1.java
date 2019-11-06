package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import parser.TokenParser;

class TokenParserTest1 {

	@Test
	void test() {
		List<String> input = Arrays.asList("foo", ":=", ":=", "23", "write", "foo", "$$");
		
		Node result = TokenParser.functionParser(input);
		
		assertEquals(result.getChildren().size(), 0);
		assertEquals(result.getLabel(), "Illegal");

	}

}
