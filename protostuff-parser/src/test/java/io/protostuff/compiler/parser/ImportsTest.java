package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Import;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ImportsTest {


    private Injector injector;
    private Importer importer;

    @BeforeEach
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
        importer = injector.getInstance(Importer.class);
    }

    @Test
    public void test() throws Exception {
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/imports_a.proto");
        Import anImport = context.getProto().getImports().get(0);
        assertEquals("protostuff_unittest/imports_a.proto", anImport.getSourceCodeLocation().getFile());
        assertEquals(5, anImport.getSourceCodeLocation().getLine());
        assertNotNull(context.resolve(".protostuff_unittest.A"));
        assertNotNull(context.resolve(".protostuff_unittest.B"));
        assertNotNull(context.resolve(".protostuff_unittest.C"));
        assertNull(context.resolve(".protostuff_unittest.D"));
    }

    @Test
    public void duplicate() throws Exception {
        Throwable exception = expectThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/imports_duplicate.proto");
        });
        assertEquals("Cannot register duplicate type: .protostuff_unittest.A " +
                "[protostuff_unittest/imports_duplicate.proto:7]", exception.getMessage());
    }
}
