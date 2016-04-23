package io.protostuff.compiler.parser;

import org.junit.Before;
import org.junit.Test;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Oneof;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Service;

import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CommentsTest extends AbstractParserTest {

    private ProtoContext context;

    @Before
    public void setUp() throws Exception {
        context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/comments_sample.proto");
    }

    @Test
    public void testProtoComments() throws Exception {
        Proto proto = context.getProto();
        assertEquals("Proto file comment.\nMultiline.", proto.getComments());
    }

    @Test
    public void testMessageFieldComments() throws Exception {
        Message message = context.resolve(".protostuff_unittest.Message", Message.class);
        assertEquals("Message comment", message.getComments());
        assertEquals("leading field comment", message.getField("a").getComments());
        assertEquals("trailing field comment", message.getField("b").getComments());
        assertEquals("another trailing field comment", message.getField("c").getComments());
        assertEquals("leading map field comment", message.getField("m1").getComments());
        assertEquals("trailing map field comment", message.getField("m2").getComments());
    }

    @Test
    public void testOneOfComments() throws Exception {
        Message message = context.resolve(".protostuff_unittest.Message", Message.class);
        Oneof oneof = message.getOneof("Oneof");
        assertEquals("Oneof comment", oneof.getComments());
        assertEquals("leading oneof field comment", oneof.getField("x").getComments());
        assertEquals("trailing oneof field comment", oneof.getField("y").getComments());
    }

    @Test
    public void testEnumComments() throws Exception {
        Enum anEnum = context.resolve(".protostuff_unittest.Enum", Enum.class);
        assertEquals("Enum comment", anEnum.getComments());
        assertEquals("leading enum constant comment", anEnum.getConstant("A").getComments());
        assertEquals("trailing enum constant comment", anEnum.getConstant("B").getComments());
    }

    @Test
    public void testServiceComments() throws Exception {
        Service service = context.resolve(".protostuff_unittest.Service", Service.class);
        assertEquals("Service comment", service.getComments());
        assertEquals("leading rpc method comment", service.getMethod("a").getComments());
        assertEquals("trailing rpc method comment", service.getMethod("b").getComments());
    }

    @Test
    public void testMessageComments_withLeadingWhitespace() throws Exception {
        Message message = context.resolve(".protostuff_unittest.CommonLeadingWhitespace", Message.class);
        assertEquals("line 1\nline 2", message.getComments());
    }

    @Test
    public void testMessageComments_withDifferentLeadingWhitespaceCount() throws Exception {
        Message message = context.resolve(".protostuff_unittest.CommonLeadingWhitespace_andIndentation", Message.class);
        assertEquals("line 1\n  line 2", message.getComments());
    }

    @Test
    public void testMessageComments_noLeadingWhitespace() throws Exception {
        Message message = context.resolve(".protostuff_unittest.NoWhitespace", Message.class);
        assertEquals("line 1\nline 2", message.getComments());
    }

    @Test
    public void testMessageComments_empty() throws Exception {
        Message message = context.resolve(".protostuff_unittest.EmptyComment", Message.class);
        assertEquals("", message.getComments());
    }
}
