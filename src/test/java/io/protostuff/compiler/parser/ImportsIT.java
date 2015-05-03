package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ImportsIT {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("test/imports/a.proto");
        Assert.assertNotNull(context.resolve(".test.imports.A"));
        Assert.assertNotNull(context.resolve(".test.imports.B"));
        Assert.assertNotNull(context.resolve(".test.imports.C"));
        Assert.assertNull(context.resolve(".test.imports.D"));
    }
}
