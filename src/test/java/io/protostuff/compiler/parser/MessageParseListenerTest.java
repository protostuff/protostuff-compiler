package io.protostuff.compiler.parser;

import com.google.common.base.Joiner;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.MessageField;
import io.protostuff.compiler.model.Proto;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageParseListenerTest {

    public static final String SIMPLE_MESSAGE = Joiner.on('\n').join(
            "message A {",
            "  int32 x = 1;",
            "}"
    );

    public static final String EMBEDDED_MESSAGE = Joiner.on('\n').join(
            "message A {",
            "  int32 x = 1;",
            "  message B {",
            "    int32 y = 0x02;",
            "  }",
            "}"
    );

    @Test
    public void parseSimpleMessage() throws Exception {
        Message message = parseMessages(SIMPLE_MESSAGE);
        assertEquals("A", message.getName());
        assertEquals(1, message.getFields().size());
        MessageField field = message.getFields().get(0);
        assertEquals("int32", field.getTypeName());
        assertEquals("x", field.getName());
        assertEquals(1, field.getTag());
    }

    @Test
    public void parseEmbeddedMessage() throws Exception {
        Message message = parseMessages(EMBEDDED_MESSAGE);
        System.out.println(message);
    }

    private Message parseMessages(String input) {
        CharStream stream = new ANTLRInputStream(input);
        ProtoLexer lexer = new ProtoLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(TestUtils.ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ProtoParser parser = new ProtoParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(TestUtils.ERROR_LISTENER);
        ProtoContext context = new ProtoContext("test.proto");
        MessageParseListener messageParseListener = new MessageParseListener(context);
        OptionParseListener optionParseListener = new OptionParseListener(context);
        Proto proto = new Proto();
        context.push(proto);
        parser.addParseListener(messageParseListener);
        parser.addParseListener(optionParseListener);
        parser.messageBlock();
        return proto.getMessages().get(0);
    }
}
