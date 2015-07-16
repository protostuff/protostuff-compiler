package io.protostuff.compiler.parser;

import org.junit.Test;

/**
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
