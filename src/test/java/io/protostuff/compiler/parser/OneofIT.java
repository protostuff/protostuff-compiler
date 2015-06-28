package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class OneofIT extends AbstractParserTest {

    @Test
    public void testSample() throws Exception {
        ProtoContext context = importer.importFile("protostuff_unittest/oneof_sample.proto");

        Message m = context.resolve(".protostuff_unittest.SampleMessage", Message.class);

        Oneof oneof = m.getOneof("test_oneof");
        Assert.assertEquals("test_oneof", oneof.getName());
        Assert.assertEquals(".protostuff_unittest.SampleMessage.", oneof.getNamespace());
        Field name = oneof.getField("name");
        Assert.assertEquals(ScalarFieldType.STRING, name.getType());
        Assert.assertEquals(4, name.getTag());
        Assert.assertFalse(name.hasModifier());

        Field subMessage = oneof.getField("sub_message");
        Assert.assertEquals("SubMessage", subMessage.getType().getName());
        Assert.assertEquals(9, subMessage.getTag());
        Assert.assertFalse(subMessage.hasModifier());

    }
}
