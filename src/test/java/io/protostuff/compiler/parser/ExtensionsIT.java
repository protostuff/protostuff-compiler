package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ExtensionsIT {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Injector injector;
    private Importer importer;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
        importer = injector.getInstance(Importer.class);
    }

    @Test
    public void testBasicExtensions() throws Exception {
        ProtoContext context = importer.importFile("protostuff_unittest/extensions_sample.proto");
        Proto proto = context.getProto();

        Message a = proto.getMessage("A");
        assertNotNull(a);
        List<Extension> extensions = a.getExtensions();
        Assert.assertEquals(1, extensions.size());
        Field ay = a.getExtensionField(".protostuff_unittest.ay");
        assertNotNull(ay);
        assertEquals(ScalarFieldType.INT32, ay.getType());
        assertEquals(42, ay.getTag());
        Field az = a.getExtensionField(".protostuff_unittest.az");
        assertNotNull(az);
        assertEquals(ScalarFieldType.INT32, az.getType());
        assertEquals(43, az.getTag());

        assertEquals(1, a.getExtensionRanges().size());
        ExtensionRange aRange = a.getExtensionRanges().get(0);
        assertEquals(10, aRange.getMin());
        assertEquals(Field.MAX_TAG_VALUE, aRange.getMax());

        Message b = a.getMessage("B");
        assertNotNull(b);
        Assert.assertEquals(2, b.getExtensions().size());
        Field by = b.getExtensionField(".protostuff_unittest.A.by");
        assertNotNull(by);
        assertEquals(ScalarFieldType.INT32, by.getType());
        assertEquals(52, by.getTag());
        Field bz = b.getExtensionField(".protostuff_unittest.bz");
        assertNotNull(bz);
        assertEquals(ScalarFieldType.INT32, bz.getType());
        assertEquals(53, bz.getTag());

        assertEquals(1, b.getExtensionRanges().size());
        ExtensionRange bRange = b.getExtensionRanges().get(0);
        assertEquals(10, bRange.getMin());
        assertEquals(1000, bRange.getMax());
    }

    @Test
    public void tagOutOfRange() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Extension field 'e' tag=9 is out of allowed range " +
                "[protostuff_unittest/extensions_tag_out_of_range.proto:10]");
        importer.importFile("protostuff_unittest/extensions_tag_out_of_range.proto");
    }

    @Test
    public void badExtendeeType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot extend 'A': not a message " +
                "[protostuff_unittest/extensions_bad_extendee.proto:9]");
        importer.importFile("protostuff_unittest/extensions_bad_extendee.proto");
    }
}
