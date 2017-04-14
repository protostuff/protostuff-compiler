package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

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
        CharStream stream = CharStreams.fromString(input);
        ProtoLexer lexer = new ProtoLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(TestUtils.ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ProtoParser parser = new ProtoParser(tokenStream);
        parser.addErrorListener(TestUtils.ERROR_LISTENER);
        parser.syntaxStatement();
    }


}
