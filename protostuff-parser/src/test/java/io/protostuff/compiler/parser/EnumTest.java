package io.protostuff.compiler.parser;

import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumTest extends AbstractParserTest {

    @Test
    public void duplicateConstantName() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Duplicate enum constant name: 'X' " +
                "[protostuff_unittest/duplicate_enum_constant_name.proto:7]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_enum_constant_name.proto");
    }

    @Test
    public void duplicateConstantValue() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Duplicate enum constant value: 0 " +
                "[protostuff_unittest/duplicate_enum_constant_value.proto:7]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_enum_constant_value.proto");
    }

    @Test
    public void duplicateConstantValue_allowed() throws Exception {
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_enum_constant_value_allowed.proto");
    }
}
