package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class MyTests {

    @Test
    void empty() {
        List<String> input = Arrays.asList("$$");

        Node result = TokenParser.functionParser(input);

        assertEquals(result.getChildren().size(), 2);
        assertEquals(result.getLabel(), "start");
        assertEquals(result.getChildren().get(0).getLabel(), "stmt_list");
        assertEquals(result.getChildren().get(0).getChildren().size(), 1);
        assertEquals(result.getChildren().get(1).getLabel(), "$$");
        assertEquals(result.getChildren().get(1).getChildren().size(), 0);
    }

    @Test
    void read() {
        List<String> input = Arrays.asList("read", "A", "$$");

        Node result = TokenParser.functionParser(input);

        // start
        assertEquals(result.getLabel(), "start");
        assertEquals(result.getChildren().size(), 2);
        Node stmt_list = result.getChildren().get(0);
        Node end = result.getChildren().get(1);

        // stmt_list
        assertEquals(stmt_list.getLabel(), "stmt_list");
        assertEquals(stmt_list.getChildren().size(), 2);
        Node stmt = stmt_list.getChildren().get(0);
        Node empty_stmt_list = stmt_list.getChildren().get(1);

        // stmt
        assertEquals(stmt.getLabel(), "stmt");
        assertEquals(stmt.getChildren().size(), 2);
        Node read = stmt.getChildren().get(0);
        Node id = stmt.getChildren().get(1);

        // read
        assertEquals(read.getLabel(), "read");
        assertEquals(read.getChildren().size(), 0);

        // id
        assertEquals(id.getLabel(), "A");
        assertEquals(id.getChildren().size(), 0);

        // empty_stmt_list
        assertEquals(empty_stmt_list.getLabel(), "stmt_list");
        assertEquals(empty_stmt_list.getChildren().size(), 1);

        // end
        assertEquals(end.getLabel(), "$$");
        assertEquals(end.getChildren().size(), 0);
    }

    @Test
    void write() {
        List<String> input = Arrays.asList("write", "A", "$$");

        Node result = TokenParser.functionParser(input);

        // start
        assertEquals(result.getLabel(), "start");
        assertEquals(result.getChildren().size(), 2);
        Node stmt_list = result.getChildren().get(0);
        Node end = result.getChildren().get(1);

        // stmt_list
        assertEquals(stmt_list.getLabel(), "stmt_list");
        assertEquals(stmt_list.getChildren().size(), 2);
        Node stmt = stmt_list.getChildren().get(0);
        Node empty_stmt_list = stmt_list.getChildren().get(1);

        // stmt
        assertEquals(stmt.getLabel(), "stmt");
        assertEquals(stmt.getChildren().size(), 2);
        Node write = stmt.getChildren().get(0);
        Node expr = stmt.getChildren().get(1);

        // read
        assertEquals(write.getLabel(), "write");
        assertEquals(write.getChildren().size(), 0);

        // expr
        assertEquals(expr.getLabel(), "expr");
        assertEquals(expr.getChildren().size(), 2);
        Node term = expr.getChildren().get(0);
        Node empty_term_tail = expr.getChildren().get(1);

        // term
        assertEquals(term.getLabel(), "term");
        assertEquals(term.getChildren().size(), 2);
        Node factor = term.getChildren().get(0);
        Node empty_factor_tail = term.getChildren().get(1);

        // factor
        assertEquals(factor.getLabel(), "factor");
        assertEquals(factor.getChildren().size(), 1);
        Node id2 = factor.getChildren().get(0);

        // id2
        assertEquals(id2.getLabel(), "A");
        assertEquals(id2.getChildren().size(), 0);

        // empty_factor_tail
        assertEquals(empty_factor_tail.getLabel(), "factor_tail");
        assertEquals(empty_factor_tail.getChildren().size(), 1);

        // empty_term_tail
        assertEquals(empty_term_tail.getLabel(), "term_tail");
        assertEquals(empty_term_tail.getChildren().size(), 1);

        // empty_stmt_list
        assertEquals(empty_stmt_list.getLabel(), "stmt_list");
        assertEquals(empty_stmt_list.getChildren().size(), 1);

        // end
        assertEquals(end.getLabel(), "$$");
        assertEquals(end.getChildren().size(), 0);
    }

    @Test
    void allocation() {
        List<String> input = Arrays.asList("A", ":=", "B", "$$");

        Node result = TokenParser.functionParser(input);

        // start
        assertEquals(result.getLabel(), "start");
        assertEquals(result.getChildren().size(), 2);
        Node stmt_list = result.getChildren().get(0);
        Node end = result.getChildren().get(1);

        // stmt_list
        assertEquals(stmt_list.getLabel(), "stmt_list");
        assertEquals(stmt_list.getChildren().size(), 2);
        Node stmt = stmt_list.getChildren().get(0);
        Node empty_stmt_list = stmt_list.getChildren().get(1);

        // stmt
        assertEquals(stmt.getLabel(), "stmt");
        assertEquals(stmt.getChildren().size(), 3);
        Node id = stmt.getChildren().get(0);
        Node alloc = stmt.getChildren().get(1);
        Node expr = stmt.getChildren().get(2);

        // read
        assertEquals(id.getLabel(), "A");
        assertEquals(id.getChildren().size(), 0);

        // alloc
        assertEquals(alloc.getLabel(), ":=");
        assertEquals(alloc.getChildren().size(), 0);

        // expr
        assertEquals(expr.getLabel(), "expr");
        assertEquals(expr.getChildren().size(), 2);
        Node term = expr.getChildren().get(0);
        Node empty_term_tail = expr.getChildren().get(1);

        // term
        assertEquals(term.getLabel(), "term");
        assertEquals(term.getChildren().size(), 2);
        Node factor = term.getChildren().get(0);
        Node empty_factor_tail = term.getChildren().get(1);

        // factor
        assertEquals(factor.getLabel(), "factor");
        assertEquals(factor.getChildren().size(), 1);
        Node id2 = factor.getChildren().get(0);

        // id2
        assertEquals(id2.getLabel(), "B");
        assertEquals(id2.getChildren().size(), 0);

        // empty_factor_tail
        assertEquals(empty_factor_tail.getLabel(), "factor_tail");
        assertEquals(empty_factor_tail.getChildren().size(), 1);

        // empty_term_tail
        assertEquals(empty_term_tail.getLabel(), "term_tail");
        assertEquals(empty_term_tail.getChildren().size(), 1);

        // empty_stmt_list
        assertEquals(empty_stmt_list.getLabel(), "stmt_list");
        assertEquals(empty_stmt_list.getChildren().size(), 1);

        // end
        assertEquals(end.getLabel(), "$$");
        assertEquals(end.getChildren().size(), 0);
    }

    @Test
    void double_read() {
        List<String> input = Arrays.asList("read", "A", "read", "B", "$$");

        Node result = TokenParser.functionParser(input);

        // start
        assertEquals(result.getLabel(), "start");
        assertEquals(result.getChildren().size(), 2);
        Node stmt_list = result.getChildren().get(0);
        Node end = result.getChildren().get(1);

        // stmt_list
        assertEquals(stmt_list.getLabel(), "stmt_list");
        assertEquals(stmt_list.getChildren().size(), 2);
        Node stmt1 = stmt_list.getChildren().get(0);
        Node stmt_list2 = stmt_list.getChildren().get(1);

        // stmt1
        assertEquals(stmt1.getLabel(), "stmt");
        assertEquals(stmt1.getChildren().size(), 2);
        Node read = stmt1.getChildren().get(0);
        Node id = stmt1.getChildren().get(1);

        // read
        assertEquals(read.getLabel(), "read");
        assertEquals(read.getChildren().size(), 0);

        // id
        assertEquals(id.getLabel(), "A");
        assertEquals(id.getChildren().size(), 0);

        // stmt_list2
        assertEquals(stmt_list2.getLabel(), "stmt_list");
        assertEquals(stmt_list2.getChildren().size(), 2);
        Node stmt2 = stmt_list2.getChildren().get(0);
        Node empty_stmt_list = stmt_list2.getChildren().get(1);

        // stmt2
        assertEquals(stmt2.getLabel(), "stmt");
        assertEquals(stmt2.getChildren().size(), 2);
        Node read2 = stmt2.getChildren().get(0);
        Node id2 = stmt2.getChildren().get(1);

        // read
        assertEquals(read2.getLabel(), "read");
        assertEquals(read2.getChildren().size(), 0);

        // id
        assertEquals(id2.getLabel(), "B");
        assertEquals(id2.getChildren().size(), 0);

        // empty_stmt_list
        assertEquals(empty_stmt_list.getLabel(), "stmt_list");
        assertEquals(empty_stmt_list.getChildren().size(), 1);

        // end
        assertEquals(end.getLabel(), "$$");
        assertEquals(end.getChildren().size(), 0);
    }

    @Test
    void sum() {
        List<String> input = Arrays.asList("sum", ":=", "A", "+", "B", "$$");

        Node result = TokenParser.functionParser(input);

        // start
        assertEquals(result.getLabel(), "start");
        assertEquals(result.getChildren().size(), 2);
        Node stmt_list = result.getChildren().get(0);
        Node end = result.getChildren().get(1);

        // stmt_list
        assertEquals(stmt_list.getLabel(), "stmt_list");
        assertEquals(stmt_list.getChildren().size(), 2);
        Node stmt = stmt_list.getChildren().get(0);
        Node empty_stmt_list = stmt_list.getChildren().get(1);

        // stmt
        assertEquals(stmt.getLabel(), "stmt");
        assertEquals(stmt.getChildren().size(), 3);
        Node sum = stmt.getChildren().get(0);
        Node alloc = stmt.getChildren().get(1);
        Node expr = stmt.getChildren().get(2);

        // sum
        assertEquals(sum.getLabel(), "sum");
        assertEquals(sum.getChildren().size(), 0);

        // alloc
        assertEquals(alloc.getLabel(), ":=");
        assertEquals(alloc.getChildren().size(), 0);

        // expr
        assertEquals(expr.getLabel(), "expr");
        assertEquals(expr.getChildren().size(), 2);
        Node term1 = expr.getChildren().get(0);
        Node term_tail1 = expr.getChildren().get(1);

        // term1
        assertEquals(term1.getLabel(), "term");
        assertEquals(term1.getChildren().size(), 2);
        Node factor1 = term1.getChildren().get(0);
        Node empty_factor_tail1 = term1.getChildren().get(1);

        // factor1
        assertEquals(factor1.getLabel(), "factor");
        assertEquals(factor1.getChildren().size(), 1);
        Node id1 = factor1.getChildren().get(0);

        // id1
        assertEquals(id1.getLabel(), "A");
        assertEquals(id1.getChildren().size(), 0);

        // empty_factor_tail1
        assertEquals(empty_factor_tail1.getLabel(), "factor_tail");
        assertEquals(empty_factor_tail1.getChildren().size(), 1);

        // term_tail1
        assertEquals(term_tail1.getLabel(), "term_tail");
        assertEquals(term_tail1.getChildren().size(), 3);
        Node add_op = term_tail1.getChildren().get(0);
        Node term2 = term_tail1.getChildren().get(1);
        Node empty_term_tail = term_tail1.getChildren().get(2);

        // add_op
        assertEquals(add_op.getLabel(), "add_op");
        assertEquals(add_op.getChildren().size(), 1);
        Node plus = add_op.getChildren().get(0);

        // plus
        assertEquals(plus.getLabel(), "+");
        assertEquals(plus.getChildren().size(), 0);

        // term2
        assertEquals(term2.getLabel(), "term");
        assertEquals(term2.getChildren().size(), 2);
        Node factor2 = term2.getChildren().get(0);
        Node empty_factor_tail2 = term2.getChildren().get(1);

        // factor2
        assertEquals(factor2.getLabel(), "factor");
        assertEquals(factor2.getChildren().size(), 1);
        Node id2 = factor2.getChildren().get(0);

        // id2
        assertEquals(id2.getLabel(), "B");
        assertEquals(id2.getChildren().size(), 0);

        // empty_factor_tail2
        assertEquals(empty_factor_tail2.getLabel(), "factor_tail");
        assertEquals(empty_factor_tail2.getChildren().size(), 1);

        // empty_term_tail
        assertEquals(empty_term_tail.getLabel(), "term_tail");
        assertEquals(empty_term_tail.getChildren().size(), 1);

        // empty_stmt_list
        assertEquals(empty_stmt_list.getLabel(), "stmt_list");
        assertEquals(empty_stmt_list.getChildren().size(), 1);

        // end
        assertEquals(end.getLabel(), "$$");
        assertEquals(end.getChildren().size(), 0);
    }

    void brackets() {
        List<String> input = Arrays.asList("write", "(", "A", "+", "42", ")", "/", "B", "$$");

        Node start = TokenParser.functionParser(input);

        // start
        assertEquals(start.getLabel(), "start");
        assertEquals(start.getChildren().size(), 2);

    }
}
