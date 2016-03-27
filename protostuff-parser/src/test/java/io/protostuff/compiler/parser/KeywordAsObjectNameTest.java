package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class KeywordAsObjectNameTest {

    @Test
    public void messageNamedMessage() {
        String input = "message message{}";
        ProtoParser parser = createParser(input);
        ProtoParser.MessageBlockContext context = parser.messageBlock();
        Assert.assertEquals("message", context.name().getText());
    }

    @Test
    public void enumNamedEnum() {
        String input = "enum enum{}";
        ProtoParser parser = createParser(input);
        ProtoParser.EnumBlockContext context = parser.enumBlock();
        Assert.assertEquals("enum", context.name().getText());
    }

    @Test
    public void enumConstantNamedEnum() {
        String input = "enum = 1;";
        ProtoParser parser = createParser(input);
        ProtoParser.EnumConstantContext context = parser.enumConstant();
        Assert.assertEquals("enum", context.name().getText());
    }

    @Test
    public void fieldNamedMessage() {
        String input = "optional int32 message = 1;";
        ProtoParser parser = createParser(input);
        ProtoParser.FieldContext context = parser.field();
        Assert.assertEquals("message", context.name().getText());
    }

    private ProtoParser createParser(String input) {
        CharStream stream = new ANTLRInputStream(input);
        ProtoLexer lexer = new ProtoLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(TestUtils.ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ProtoParser parser = new ProtoParser(tokenStream);
        parser.addErrorListener(TestUtils.ERROR_LISTENER);
        return parser;
    }


}
