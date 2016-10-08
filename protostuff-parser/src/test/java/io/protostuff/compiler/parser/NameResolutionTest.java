package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class NameResolutionTest {

    private Injector injector;

    @BeforeEach
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/messages_name_resolution.proto");
        Message a = context.getProto().getMessage("A");
        assertNotNull(a);
        checkFieldType(a, "c0", ".protostuff_unittest.A.B.C");
        checkFieldType(a, "c1", ".protostuff_unittest.A.B.C");
        checkFieldType(a, "c2", ".protostuff_unittest.A.B.C");
        checkFieldType(a, "c4", ".protostuff_unittest.A.B.C");
    }

    private void checkFieldType(Message a, String field, String fieldType) {
        Field c0 = a.getField(field);
        assertNotNull(c0);
        assertEquals(fieldType, c0.getType().getFullyQualifiedName(), field);
    }

}
