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
public class MessagesTest extends AbstractParserTest {

    @Test
    public void unresolvedFieldType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Unresolved reference: 'NonExistingMessage' " +
                "[protostuff_unittest/messages_unresolved_field_type.proto:6]");
        importer.importFile("protostuff_unittest/messages_unresolved_field_type.proto");
    }

}
