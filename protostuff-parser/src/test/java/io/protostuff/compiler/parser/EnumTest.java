package io.protostuff.compiler.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumTest extends AbstractParserTest {

    @Test
    public void duplicateConstantName() throws Exception {
        String message = "Duplicate enum constant name: 'X' " +
                "[protostuff_unittest/duplicate_enum_constant_name.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_enum_constant_name.proto");
        });
        assertEquals(message, exception.getMessage());

    }

    @Test
    public void duplicateConstantValue() throws Exception {
        String message = "Duplicate enum constant value: 0 " +
                "[protostuff_unittest/duplicate_enum_constant_value.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_enum_constant_value.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void duplicateConstantValue_allowed() throws Exception {
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_enum_constant_value_allowed.proto");
    }

    @Test
    public void reserved_field_tag() throws Exception {
        String message = "Reserved enum tag: 1 " +
                "[protostuff_unittest/reserved_enum_tag.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/reserved_enum_tag.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void reserved_field_name() throws Exception {
        String message = "Reserved enum name: 'Y' " +
                "[protostuff_unittest/reserved_enum_name.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/reserved_enum_name.proto");
        });
        assertEquals(message, exception.getMessage());
    }
}
