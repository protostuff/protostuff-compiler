package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Oneof;
import io.protostuff.compiler.model.Service;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CommentsTest extends AbstractParserTest {

    private ProtoContext context;

    @Before
    public void setUp() throws Exception {
        context = importer.importFile("protostuff_unittest/comments_sample.proto");
    }

    @Test
    public void testMessageFieldComments() throws Exception {
        Message message = context.resolve(".protostuff_unittest.Message", Message.class);
        assertEquals("Message comment", message.getCommentLines().get(0));
        assertEquals("leading field comment", message.getField("a").getCommentLines().get(0));
        assertEquals("trailing field comment", message.getField("b").getCommentLines().get(0));
        assertEquals("leading map field comment", message.getField("m1").getCommentLines().get(0));
        assertEquals("trailing map field comment", message.getField("m2").getCommentLines().get(0));
    }

    @Test
    public void testOneOfComments() throws Exception {
        Message message = context.resolve(".protostuff_unittest.Message", Message.class);
        Oneof oneof = message.getOneof("Oneof");
        assertEquals("Oneof comment", oneof.getCommentLines().get(0));
        assertEquals("leading oneof field comment", oneof.getField("x").getCommentLines().get(0));
        assertEquals("trailing oneof field comment", oneof.getField("y").getCommentLines().get(0));
    }

    @Test
    public void testEnumComments() throws Exception {
        Enum anEnum = context.resolve(".protostuff_unittest.Enum", Enum.class);
        assertEquals("Enum comment", anEnum.getCommentLines().get(0));
        assertEquals("leading enum constant comment", anEnum.getConstant("A").getCommentLines().get(0));
        assertEquals("trailing enum constant comment", anEnum.getConstant("B").getCommentLines().get(0));
    }

    @Test
    public void testServiceComments() throws Exception {
        Service service = context.resolve(".protostuff_unittest.Service", Service.class);
        assertEquals("Service comment", service.getCommentLines().get(0));
        assertEquals("leading rpc method comment", service.getMethod("a").getCommentLines().get(0));
        assertEquals("trailing rpc method comment", service.getMethod("b").getCommentLines().get(0));
    }

    @Test
    public void testMessageComments_withLeadingWhitespace() throws Exception {
        Message message = context.resolve(".protostuff_unittest.CommonLeadingWhitespace", Message.class);
        assertEquals("line 1", message.getCommentLines().get(0));
        assertEquals("line 2", message.getCommentLines().get(1));
    }

    @Test
    public void testMessageComments_withDifferentLeadingWhitespaceCount() throws Exception {
        Message message = context.resolve(".protostuff_unittest.CommonLeadingWhitespace_andIndentation", Message.class);
        assertEquals("line 1", message.getCommentLines().get(0));
        assertEquals("  line 2", message.getCommentLines().get(1));
    }

    @Test
    public void testMessageComments_noLeadingWhitespace() throws Exception {
        Message message = context.resolve(".protostuff_unittest.NoWhitespace", Message.class);
        assertEquals("line 1", message.getCommentLines().get(0));
        assertEquals("line 2", message.getCommentLines().get(1));
    }

    @Test
    public void testMessageComments_empty() throws Exception {
        Message message = context.resolve(".protostuff_unittest.EmptyComment", Message.class);
        assertEquals("", message.getCommentLines().get(0));
    }
}
