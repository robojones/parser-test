package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class TokenParserTest2 {

	@Test
	void test() {
		List<String> input = Arrays.asList("read", "A", "read","B","sum", ":=", "A","+", "B", "write", "sum", "write", "sum","/","2", "$$");
		
		Node result = TokenParser.functionParser(input);
		
		assertEquals(result.getChildren().size(), 2);
		assertEquals(result.getChildren().get(0).getLabel(), "stmt_list");
		assertEquals(result.getChildren().get(1).getLabel(), "$$");
		assertEquals(result.getChildren().get(0).getChildren().size(), 2);
		assertEquals(result.getChildren().get(0).getChildren().get(0).getChildren().get(1).getLabel(), "A");
		// add more assertions to check rest of tree
	}

}
