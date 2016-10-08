package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Range;
import io.protostuff.compiler.model.ScalarFieldType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;


/**
 * @author Kostiantyn Shchepanovskyi
 */
@SuppressWarnings({"ConstantConditions", "ThrowableResultOfMethodCallIgnored"})
public class ExtensionsTest {

    private Importer importer;

    @BeforeEach
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new ParserModule());
        importer = injector.getInstance(Importer.class);
    }

    @Test
    public void testBasicExtensions() throws Exception {
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/extensions_sample.proto");
        ExtensionRegistry er = context.getExtensionRegistry();

        Message a = context.resolve(Message.class, ".protostuff_unittest.A");
        Map<String, Field> aFields = er.getExtensionFields(a);
        assertEquals(2, aFields.size());
        Field ay = aFields.get(".protostuff_unittest.ay");
        assertEquals(ScalarFieldType.INT32, ay.getType());
        assertEquals(42, ay.getTag());
        Field az = aFields.get(".protostuff_unittest.az");
        assertEquals(ScalarFieldType.INT32, az.getType());
        assertEquals(43, az.getTag());
        assertEquals(1, a.getExtensionRanges().size());
        Range aRange = a.getExtensionRanges().get(0);
        assertEquals(10, aRange.getFrom());
        assertEquals(Field.MAX_TAG_VALUE, aRange.getTo());

        Message b = context.resolve(Message.class, ".protostuff_unittest.A.B");
        Map<String, Field> bFields = er.getExtensionFields(b);
        assertEquals(2, bFields.size());
        Field by = bFields.get(".protostuff_unittest.A.by");
        assertEquals(ScalarFieldType.INT32, by.getType());
        assertEquals(52, by.getTag());
        Field bz = bFields.get(".protostuff_unittest.bz");
        assertEquals(ScalarFieldType.INT32, bz.getType());
        assertEquals(53, bz.getTag());
        assertEquals(1, b.getExtensionRanges().size());
        Range bRange = b.getExtensionRanges().get(0);
        assertEquals(10, bRange.getFrom());
        assertEquals(1000, bRange.getTo());
    }

    @Test
    public void tagOutOfRange() throws Exception {
        ParserException exception = expectThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/extensions_tag_out_of_range.proto");
        });
        assertEquals("Extension field 'e' tag=9 is out of allowed range " +
                "[protostuff_unittest/extensions_tag_out_of_range.proto:10]", exception.getMessage());
    }

    @Test
    public void badExtendeeType() throws Exception {
        ParserException exception = expectThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/extensions_bad_extendee.proto");
        });
        assertEquals("Cannot extend 'A': not a message " +
                "[protostuff_unittest/extensions_bad_extendee.proto:9]", exception.getMessage());
    }
}
