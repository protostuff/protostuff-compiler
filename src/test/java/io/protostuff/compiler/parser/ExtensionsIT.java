package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ExtensionsIT {
    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("test/extensions/test.proto");
        Proto proto = context.getProto();

        Message a = proto.getMessage("A");
        assertNotNull(a);
        List<Extension> extensions = a.getExtensions();
        Assert.assertEquals(1, extensions.size());
        Field ay = a.getExtensionField(".test.extensions.ay");
        assertNotNull(ay);
        assertEquals(ScalarFieldType.INT32, ay.getType());
        assertEquals(42, ay.getTag());
        Field az = a.getExtensionField(".test.extensions.az");
        assertNotNull(az);
        assertEquals(ScalarFieldType.INT32, az.getType());
        assertEquals(43, az.getTag());

        Message b = a.getMessage("B");
        assertNotNull(b);
        Assert.assertEquals(2, b.getExtensions().size());
        Field by = b.getExtensionField(".test.extensions.A.by");
        assertNotNull(by);
        assertEquals(ScalarFieldType.INT32, by.getType());
        assertEquals(52, by.getTag());
        Field bz = b.getExtensionField(".test.extensions.bz");
        assertNotNull(bz);
        assertEquals(ScalarFieldType.INT32, bz.getType());
        assertEquals(53, bz.getTag());
    }

}
