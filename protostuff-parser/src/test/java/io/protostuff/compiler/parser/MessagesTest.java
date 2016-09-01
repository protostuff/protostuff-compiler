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
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/messages_unresolved_field_type.proto");
    }

    @Test
    public void duplicate_field_tag() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        thrown.expect(ParserException.class);
        thrown.expectMessage("Duplicate field tag: 1 " +
                "[protostuff_unittest/duplicate_field_tag.proto:7]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_field_tag.proto");
    }

    @Test
    public void duplicate_field_name() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        thrown.expect(ParserException.class);
        thrown.expectMessage("Duplicate field name: 'x' " +
                "[protostuff_unittest/duplicate_field_name.proto:7]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_field_name.proto");
    }

    @Test
    public void invalid_field_tag() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        thrown.expect(ParserException.class);
        thrown.expectMessage("Invalid tag: 19001, allowed range is [1, 19000) and (19999, 536870911] " +
                "[protostuff_unittest/invalid_field_tag_value.proto:7]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/invalid_field_tag_value.proto");
    }

    @Test
    public void reserved_field_tag() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        thrown.expect(ParserException.class);
        thrown.expectMessage("Reserved field tag: 5 " +
                "[protostuff_unittest/reserved_field_tag.proto:6]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/reserved_field_tag.proto");
    }

    @Test
    public void reserved_field_name() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        thrown.expect(ParserException.class);
        thrown.expectMessage("Reserved field name: 'x' " +
                "[protostuff_unittest/reserved_field_name.proto:6]");
        importer.importFile(new ClasspathFileReader(), "protostuff_unittest/reserved_field_name.proto");
    }
}
