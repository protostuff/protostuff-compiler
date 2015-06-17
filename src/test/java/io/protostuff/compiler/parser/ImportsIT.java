package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Import;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        Import anImport = context.getProto().getImports().get(0);
        assertEquals("test/imports/a.proto", anImport.getSourceCodeLocation().getFile());
        assertEquals(5, anImport.getSourceCodeLocation().getLine());
        assertNotNull(context.resolve(".test.imports.A"));
        assertNotNull(context.resolve(".test.imports.B"));
        assertNotNull(context.resolve(".test.imports.C"));
        assertNull(context.resolve(".test.imports.D"));
    }
}
