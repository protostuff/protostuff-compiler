package io.protostuff.parser;

import com.google.common.base.Joiner;
import io.protostuff.proto3.Message;
import io.protostuff.proto3.MessageField;
import io.protostuff.proto3.FileDescriptor;
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
        assertEquals("int32", field.getType());
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
        Proto3Lexer lexer = new Proto3Lexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(TestUtils.ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Proto3Parser parser = new Proto3Parser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(TestUtils.ERROR_LISTENER);
        Context context = new Context();
        MessageParseListener messageParseListener = new MessageParseListener(context);
        OptionParseListener optionParseListener = new OptionParseListener(context);
        FileDescriptor fileDescriptor = new FileDescriptor();
        context.push(fileDescriptor);
        parser.addParseListener(messageParseListener);
        parser.addParseListener(optionParseListener);
        parser.messageBlock();
        return fileDescriptor.getMessages().get(0);
    }
}
