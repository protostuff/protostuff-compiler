package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Before;
import org.junit.Test;

import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Import;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ImportsTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/imports_a.proto");
        Import anImport = context.getProto().getImports().get(0);
        assertEquals("protostuff_unittest/imports_a.proto", anImport.getSourceCodeLocation().getFile());
        assertEquals(5, anImport.getSourceCodeLocation().getLine());
        assertNotNull(context.resolve(".protostuff_unittest.A"));
        assertNotNull(context.resolve(".protostuff_unittest.B"));
        assertNotNull(context.resolve(".protostuff_unittest.C"));
        assertNull(context.resolve(".protostuff_unittest.D"));
    }
}
