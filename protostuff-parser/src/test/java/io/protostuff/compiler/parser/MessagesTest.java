package io.protostuff.compiler.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class MessagesTest extends AbstractParserTest {

    @Test
    public void unresolvedFieldType() throws Exception {
        String message = "Unresolved reference: 'NonExistingMessage' " +
                "[protostuff_unittest/messages_unresolved_field_type.proto:6]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/messages_unresolved_field_type.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void duplicate_field_tag() throws Exception {
        String message = "Duplicate field tag: 1 " +
                "[protostuff_unittest/duplicate_field_tag.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_field_tag.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void duplicate_field_name() throws Exception {
        String message = "Duplicate field name: 'x' " +
                "[protostuff_unittest/duplicate_field_name.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_field_name.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void invalid_field_tag() throws Exception {
        String message = "Invalid tag: 19001, allowed range is [1, 19000) and (19999, 536870911] " +
                "[protostuff_unittest/invalid_field_tag_value.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/invalid_field_tag_value.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void reserved_field_tag() throws Exception {
        String message = "Reserved field tag: 5 " +
                "[protostuff_unittest/reserved_field_tag.proto:6]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/reserved_field_tag.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void reserved_field_name() throws Exception {
        String message = "Reserved field name: 'x' " +
                "[protostuff_unittest/reserved_field_name.proto:6]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/reserved_field_name.proto");
        });
        assertEquals(message, exception.getMessage());
    }
}
