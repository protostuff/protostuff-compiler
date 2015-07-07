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
    public void invalidStandardOptionType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot assign string to bool: incompatible types " +
                "[protostuff_unittest/options_illegal_type.proto:7]");
        importer.importFile("protostuff_unittest/options_illegal_type.proto");
    }

}
