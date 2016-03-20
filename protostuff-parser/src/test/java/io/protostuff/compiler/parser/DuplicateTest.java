package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Import;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class DuplicateTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void duplicate() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot register duplicate type: .protostuff_unittest.A " +
                "[protostuff_unittest/duplicate.proto:9]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate.proto");
    }
}
