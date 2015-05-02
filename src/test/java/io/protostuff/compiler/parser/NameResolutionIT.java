package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class NameResolutionIT {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("test/name_resolution/test.proto");
        Message a = context.getProto().getMessage("A");
        Assert.assertNotNull(a);
        checkFieldType(a, "c0", ".test.name_resolution.A.B.C");
        checkFieldType(a, "c1", ".test.name_resolution.A.B.C");
        checkFieldType(a, "c2", ".test.name_resolution.A.B.C");
        checkFieldType(a, "c3", ".test.name_resolution.A.B.C");
        checkFieldType(a, "c4", ".test.name_resolution.A.B.C");
    }

    private void checkFieldType(Message a, String field, String fieldType) {
        Field c0 = a.getField(field);
        Assert.assertNotNull(c0);
        Assert.assertEquals(field, fieldType, c0.getType().getReference());
    }
    
}
