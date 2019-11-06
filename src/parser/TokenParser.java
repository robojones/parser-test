package parser;

import java.util.List;

public class TokenParser {
    public enum Terminal {
        EOF,
        ID,
        LITERAL,
        READ,
        WRITE,
        ASSIGN,
        ADD,
        SUBTR,
        MULT,
        DIV,
        OPEN_BRACE,
        CLOSE_BRACE,
    }

    private List<String> input;
    private int cursor = 0;
    private int id = 0;

    private Node root;

    private final Node error = createNode(null, "Illegal");

    private boolean hadError = false;

    /**
     * Check if the current token matches the expected terminal type.
     *
     * @param expected Expected terminal type of the current token.
     * @return True if the token matches the terminal type.
     */
    private boolean peek(Terminal expected) {
        if (cursor >= input.size()) {
            return false;
        }

        String token = input.get(cursor);
        switch (expected) {
            case EOF:
                return token.equals("$$");
            case ADD:
                return token.equals("+");
            case SUBTR:
                return token.equals("-");
            case MULT:
                return token.equals("*");
            case DIV:
                return token.equals("/");
            case ASSIGN:
                return token.equals(":=");
            case OPEN_BRACE:
                return token.equals("(");
            case CLOSE_BRACE:
                return token.equals(")");
            case READ:
                return token.equals("read");
            case WRITE:
                return token.equals("write");
            case LITERAL:
                return token.matches("\\d+");
            case ID:
                // ID must me anything but the other tokens.
                return !peek(Terminal.EOF)
                        && !peek(Terminal.ADD)
                        && !peek(Terminal.SUBTR)
                        && !peek(Terminal.MULT)
                        && !peek(Terminal.DIV)
                        && !peek(Terminal.ASSIGN)
                        && !peek(Terminal.OPEN_BRACE)
                        && !peek(Terminal.CLOSE_BRACE)
                        && !peek(Terminal.READ)
                        && !peek(Terminal.WRITE)
                        && !peek(Terminal.LITERAL);
        }
        return false;
    }

    private void consume(Node parent, Terminal expected) {
        if (cursor >= input.size()) {
            error();
            return;
        }

        String token = input.get(cursor);

        if (peek(expected)) {
            createNode(parent, token);
            cursor += 1;
        } else {
            error();
        }
    }

    private void start() {
        Node node = createNode(null, "start");
        root = node;
        stmt_list(node);
        consume(node, Terminal.EOF);
    }

    private void stmt_list(Node parent) {
        Node node = createNode(parent, "stmt_list");
        if (peek(Terminal.ID)
                || peek(Terminal.READ)
                || peek(Terminal.WRITE)) {
            stmt(node);
            stmt_list(node);
        } else {
            epsilon(node);
        }
    }

    private void stmt(Node parent) {
        Node node = createNode(parent, "stmt");
        if (peek(Terminal.ID)) {
            consume(node, Terminal.ID);
            consume(node, Terminal.ASSIGN);
            expr(node);
        } else if (peek(Terminal.READ)) {
            consume(node, Terminal.READ);
            consume(node, Terminal.ID);
        } else if (peek(Terminal.WRITE)) {
            consume(node, Terminal.WRITE);
            expr(node);
        } else {
            error();
        }
    }

    private void expr(Node parent) {
        Node node = createNode(parent, "expr");
        term(node);
        term_tail(node);
    }

    private void term_tail(Node parent) {
        Node node = createNode(parent, "term_tail");
        if (peek(Terminal.ADD) || peek(Terminal.SUBTR)) {
            add_op(node);
            term(node);
            term_tail(node);
        } else {
            epsilon(node);
        }
    }

    private void term(Node parent) {
        Node node = createNode(parent, "term");
        factor(node);
        factor_tail(node);
    }

    private void factor_tail(Node parent) {
        Node node = createNode(parent, "factor_tail");
        if (peek(Terminal.MULT) || peek(Terminal.DIV)) {
            mult_op(node);
            factor(node);
            factor_tail(node);
        } else {
            epsilon(node);
        }
    }

    private void factor(Node parent) {
        Node node = createNode(parent, "factor");
        if (peek(Terminal.OPEN_BRACE)) {
            consume(node, Terminal.OPEN_BRACE);
            expr(node);
            consume(node, Terminal.CLOSE_BRACE);
        } else if (peek(Terminal.ID)) {
            consume(node, Terminal.ID);
        } else if (peek(Terminal.LITERAL)) {
            consume(node, Terminal.LITERAL);
        } else {
            error();
        }
    }

    private void add_op(Node parent) {
        Node node = createNode(parent, "add_op");
        if (peek(Terminal.ADD)) {
            consume(node, Terminal.ADD);
        } else if (peek(Terminal.SUBTR)) {
            consume(node, Terminal.SUBTR);
        } else {
            error();
        }
    }


    private void mult_op(Node parent) {
        Node node = createNode(parent, "mult_op");
        if (peek(Terminal.MULT)) {
            consume(node, Terminal.MULT);
        } else if (peek(Terminal.DIV)) {
            consume(node, Terminal.DIV);
        } else {
            error();
        }
    }

    private void epsilon(Node parent) {
        createNode(parent, "epsilon");
    }

    private Node createNode(Node parent, String label) {
        Node node = new Node(id);
        id += 1;
        node.setLabel(label);
        if (parent != null) {
            node.setParent(parent);
            parent.addChildren(node);
        }
        return node;
    }

    private void error() {
        hadError = true;
    }

    private Node parse(List<String> input) {
        this.input = input;
        start();
        if (hadError) {
            return error;
        } else {
            return root;
        }
    }

    public static Node functionParser(List<String> input) {
        TokenParser parser = new TokenParser();
        return parser.parse(input);
    }
}
