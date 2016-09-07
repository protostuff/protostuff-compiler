package io.protostuff.compiler.parser;

import org.junit.Assert;
import org.junit.Test;

import io.protostuff.compiler.model.Proto;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CustomOptionsTest extends AbstractParserTest {

    @Test
    public void googleUnitTest() throws Exception {
        importer.importFile(new ClasspathFileReader(), "protobuf_unittest/unittest_custom_options.proto");
    }

    @Test
    public void sample() throws Exception {
        ProtoContext protoContext = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_sample.proto");
        Proto proto = protoContext.getProto();
        Assert.assertEquals("A", proto.getOptions().get("(.protostuff_unittest.a)").getString());
        Assert.assertEquals("B", proto.getOptions().get("(.protostuff_unittest.b)").getString());
        Assert.assertEquals("C", proto.getOptions().get("(.protostuff_unittest.c)").getString());
        Assert.assertEquals(42, proto.getOptions().get("(.protostuff_unittest.d).f").getInt32());
    }

    @Test
    public void invalidCustomOption() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Unknown option: 'unknown_option' " +
                "[protostuff_unittest/options_illegal_custom_option.proto:5]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_custom_option.proto");
    }

    @Test
    public void invalidCustomOptionType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot set option 'a': expected string value " +
                "[protostuff_unittest/options_illegal_custom_option_type.proto:7]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_custom_option_type.proto");
    }

    @Test
    public void invalidStandardOption() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Unknown option: 'non_existing_option' " +
                "[protostuff_unittest/options_illegal_standard_option.proto:7]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_standard_option.proto");
    }

    @Test
    public void invalidStandardOptionType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot set option 'deprecated': expected bool value " +
                "[protostuff_unittest/options_illegal_type.proto:7]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_type.proto");
    }

    @Test
    public void invalidStandardEnumOptionType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot set option 'optimize_for': expected enum = [SPEED, CODE_SIZE, LITE_RUNTIME] " +
                "[protostuff_unittest/options_illegal_enum_name.proto:7]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_enum_name.proto");
    }

}
