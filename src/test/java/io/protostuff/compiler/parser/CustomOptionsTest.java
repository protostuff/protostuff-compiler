package io.protostuff.compiler.parser;

import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CustomOptionsTest extends AbstractParserTest {

    @Test
    public void googleUnitTest() throws Exception {
        importer.importFile("protobuf_unittest/unittest_custom_options.proto");
    }

    @Test
    public void sample() throws Exception {
        importer.importFile("protostuff_unittest/options_sample.proto");
    }

    @Test
    public void invalidCustomOption() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Unknown option: 'unknown_option' " +
                "[protostuff_unittest/options_illegal_custom_option.proto:5]");
        importer.importFile("protostuff_unittest/options_illegal_custom_option.proto");
    }

    @Test
    public void invalidStandardOption() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Unknown option: 'non_existing_option' " +
                "[protostuff_unittest/options_illegal_standard_option.proto:7]");
        importer.importFile("protostuff_unittest/options_illegal_standard_option.proto");
    }

    @Test
    public void invalidStandardOptionType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot set option 'deprecated': expected bool value " +
                "[protostuff_unittest/options_illegal_type.proto:7]");
        importer.importFile("protostuff_unittest/options_illegal_type.proto");
    }

    @Test
    public void invalidStandardEnumOptionType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot set option 'optimize_for': expected enum = [SPEED, LITE_RUNTIME, CODE_SIZE] " +
                "[protostuff_unittest/options_illegal_enum_name.proto:7]");
        importer.importFile("protostuff_unittest/options_illegal_enum_name.proto");
    }

}
