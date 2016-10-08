package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class DuplicateTest {


    private Injector injector;

    @BeforeEach
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void duplicate_type() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ParserException exception = expectThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate.proto");
        });
        assertEquals("Cannot register duplicate type: .protostuff_unittest.A " +
                "[protostuff_unittest/duplicate.proto:9]", exception.getMessage());
    }

}
