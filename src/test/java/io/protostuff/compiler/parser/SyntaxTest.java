package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class SyntaxTest {

    public static final String PROTO3 = "syntax = \"proto3\";";
    public static final String PROTO2 = "syntax = \"proto2\";";

    @Test
    public void parseProto3SyntaxLine() throws Exception {
        parseSyntaxLine(PROTO3);
    }

    @Test
    public void parseProto2SyntaxLine() throws Exception {
        parseSyntaxLine(PROTO2);
    }

    private void parseSyntaxLine(String input) {
        CharStream stream = new ANTLRInputStream(input);
        Proto3Lexer lexer = new Proto3Lexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(TestUtils.ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Proto3Parser parser = new Proto3Parser(tokenStream);
        parser.addErrorListener(TestUtils.ERROR_LISTENER);
        parser.syntax();
    }


}
