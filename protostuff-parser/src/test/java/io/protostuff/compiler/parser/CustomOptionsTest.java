package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Oneof;
import io.protostuff.compiler.model.Proto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class CustomOptionsTest extends AbstractParserTest {

    @Test
    public void googleUnitTest() throws Exception {
        importer.importFile(new ClasspathFileReader(), "protobuf_unittest/unittest_custom_options.proto");
    }

    @Test
    public void sample() throws Exception {
        ProtoContext protoContext = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_sample.proto");
        Proto proto = protoContext.getProto();
        assertEquals("A", proto.getOptions().get("(.protostuff_unittest.a)").getString());
        assertEquals("B", proto.getOptions().get("(.protostuff_unittest.b)").getString());
        assertEquals("C", proto.getOptions().get("(.protostuff_unittest.c)").getString());
        assertEquals(42, proto.getOptions().get("(.protostuff_unittest.d).f").getInt32());
    }

    @Test
    public void oneofCustomOption() throws Exception {
        ProtoContext protoContext = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/oneof_options.proto");
        Proto proto = protoContext.getProto();
        Message message = proto.getMessage("SampleMessage");
        assertNotNull(message);
        Oneof oneof = message.getOneof("test_oneof");
        assertNotNull(oneof);
        assertEquals(-99, oneof.getOptions().get("(.protostuff_unittest.foo)").getInt32());
    }

    @Test
    public void invalidCustomOption() throws Exception {
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_custom_option.proto");
        });
        String message = "Unknown option: 'unknown_option' " +
                "[protostuff_unittest/options_illegal_custom_option.proto:5]";
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void invalidCustomOptionType() throws Exception {
        String message = "Cannot set option 'a': expected string value " +
                "[protostuff_unittest/options_illegal_custom_option_type.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_custom_option_type.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void invalidStandardOption() throws Exception {
        String message = "Unknown option: 'non_existing_option' " +
                "[protostuff_unittest/options_illegal_standard_option.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_standard_option.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void invalidStandardOptionType() throws Exception {
        String message = "Cannot set option 'deprecated': expected bool value " +
                "[protostuff_unittest/options_illegal_type.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_type.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void invalidStandardEnumOptionType() throws Exception {
        String message = "Cannot set option 'optimize_for': expected enum = [SPEED, LITE_RUNTIME, CODE_SIZE] " +
                "[protostuff_unittest/options_illegal_enum_name.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/options_illegal_enum_name.proto");
        });
        assertEquals(message, exception.getMessage());
    }

}
