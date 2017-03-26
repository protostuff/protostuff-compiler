package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class BadProtoTest {

    private Injector injector;
    private Importer importer;

    @BeforeEach
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
        importer = injector.getInstance(Importer.class);
    }

    @Test
    public void testProtoNotFound() throws Exception {
        ParserException error = Assertions.assertThrows(ParserException.class, () ->
                importer.importFile(new ClasspathFileReader(), "does_not_exist.proto"));
        Assertions.assertEquals("Can not load proto: does_not_exist.proto not found",
                error.getMessage());
    }

    @Test
    public void testGarbageInProtoFile() throws Exception {
        ParserException error = Assertions.assertThrows(ParserException.class, () ->
                importer.importFile(new ClasspathFileReader(), "protostuff_unittest/garbage.proto"));
        Assertions.assertEquals("Could not parse protostuff_unittest/garbage.proto: 1 syntax errors found",
                error.getMessage());
    }

}
