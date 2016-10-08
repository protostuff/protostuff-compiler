package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Oneof;
import io.protostuff.compiler.model.ScalarFieldType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class OneofTest extends AbstractParserTest {

    @Test
    public void testSample() throws Exception {
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/oneof_sample.proto");

        Message m = context.resolve(".protostuff_unittest.SampleMessage", Message.class);

        Oneof oneof = m.getOneof("test_oneof");
        assertEquals("test_oneof", oneof.getName());
        assertEquals(".protostuff_unittest.SampleMessage.", oneof.getNamespace());
        Field name = oneof.getField("name");
        assertEquals(ScalarFieldType.STRING, name.getType());
        assertEquals(4, name.getTag());
        assertFalse(name.hasModifier());

        Field subMessage = oneof.getField("sub_message");
        assertEquals("SubMessage", subMessage.getType().getName());
        assertEquals(9, subMessage.getTag());
        assertFalse(subMessage.hasModifier());

    }
}
