package io.protostuff.compiler.parser;

import com.google.common.base.Joiner;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Range;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.util.List;

import static io.protostuff.compiler.model.FieldModifier.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageParseListenerTest {

    @Test
    public void parseSimpleMessage() throws Exception {
        String input = Joiner.on('\n').join(
                "message A {",
                "  int32 x = 1;",
                "}"
        );
        Message message = parseMessage(input);
        assertEquals("A", message.getName());
        assertEquals(1, message.getFields().size());
        Field field = message.getFields().get(0);
        assertEquals("int32", field.getTypeName());
        assertEquals("x", field.getName());
        assertEquals(1, field.getTag());
        assertEquals(OPTIONAL, field.getModifier());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void modifiers() throws Exception {
        String input = Joiner.on('\n').join(
                "message A {",
                "  optional int32 x = 1;",
                "  required int32 y = 2;",
                "  repeated int32 z = 3;",
                "}"
        );
        Message message = parseMessage(input);
        assertEquals(OPTIONAL, message.getField("x").getModifier());
        assertEquals(REQUIRED, message.getField("y").getModifier());
        assertEquals(REPEATED, message.getField("z").getModifier());
    }

    @Test
    public void parseMessageWithFieldModifiers() throws Exception {
        Message message = parseMessage(Joiner.on('\n').join(
                "message A {",
                "  int32 x = 1;",
                "}"
        ));
        assertEquals("A", message.getName());
        assertEquals(1, message.getFields().size());
        Field field = message.getFields().get(0);
        assertEquals("int32", field.getTypeName());
        assertEquals("x", field.getName());
        assertEquals(1, field.getTag());
    }

    @Test
    public void parseEmbeddedMessage() throws Exception {
        String input = Joiner.on('\n').join(
                "message A {",
                "  int32 x = 1;",
                "  message B {",
                "    int32 y = 0x02;",
                "  }",
                "}"
        );
        Message message = parseMessage(input);
    }

    @Test
    public void parseMessageWithReservedTags() throws Exception {
        String input = Joiner.on('\n').join(
                "message A {",
                "  reserved 2, 15, 9 to 11;",
                "  reserved 'foo', 'bar';",
                "}"
        );
        Message message = parseMessage(input);
        List<Range> fieldRanges = message.getReservedFieldRanges();
        assertTrue(fieldRanges.contains(new Range(message, 2, 2)));
        assertTrue(fieldRanges.contains(new Range(message, 15, 15)));
        assertTrue(fieldRanges.contains(new Range(message, 9, 11)));
        List<String> fieldNames = message.getReservedFieldNames();
        assertTrue(fieldNames.contains("foo"));
        assertTrue(fieldNames.contains("bar"));
    }

    private Message parseMessage(String input) {
        CharStream stream = new ANTLRInputStream(input);
        ProtoLexer lexer = new ProtoLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(TestUtils.ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ProtoParser parser = new ProtoParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(TestUtils.ERROR_LISTENER);
        ProtoContext context = new ProtoContext("test.proto");
        MessageParseListener messageParseListener = new MessageParseListener(tokenStream, context);
        OptionParseListener optionParseListener = new OptionParseListener(tokenStream, context);
        Proto proto = new Proto();
        context.push(proto);
        parser.addParseListener(messageParseListener);
        parser.addParseListener(optionParseListener);
        parser.messageBlock();
        return proto.getMessages().get(0);
    }
}
