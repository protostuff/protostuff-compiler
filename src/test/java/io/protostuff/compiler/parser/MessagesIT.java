package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class MessagesIT {

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
    public void unresolvedFieldType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Unresolved reference: 'NonExistingMessage' " +
                "[protostuff_unittest/messages_unresolved_field_type.proto:6]");
        importer.importFile("protostuff_unittest/messages_unresolved_field_type.proto");
    }
}
