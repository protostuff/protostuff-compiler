package io.protostuff.compiler.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumTest extends AbstractParserTest {

    @Test
    public void duplicateConstantName() throws Exception {
        String message = "Duplicate enum constant name: 'X' " +
                "[protostuff_unittest/duplicate_enum_constant_name.proto:7]";
        ParserException exception = expectThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_enum_constant_name.proto");
        });
        assertEquals(message, exception.getMessage());

    }

    @Test
    public void duplicateConstantValue() throws Exception {
        String message = "Duplicate enum constant value: 0 " +
                "[protostuff_unittest/duplicate_enum_constant_value.proto:7]";
        ParserException exception = expectThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_enum_constant_value.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void duplicateConstantValue_allowed() throws Exception {
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_enum_constant_value_allowed.proto");
    }
}
