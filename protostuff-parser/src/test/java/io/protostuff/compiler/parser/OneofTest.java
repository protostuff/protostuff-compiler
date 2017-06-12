package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Oneof;
import io.protostuff.compiler.model.ScalarFieldType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void fieldModifierInsideOneof() throws Exception {
        String message = "Oneof field cannot have modifier: optional"
                + " [protostuff_unittest/oneof_illegal_field_modifier.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/oneof_illegal_field_modifier.proto");
        });
        assertEquals(message, exception.getMessage());
    }
}
