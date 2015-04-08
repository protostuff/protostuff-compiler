package io.protostuff.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.model.Message;
import io.protostuff.model.Proto;
import io.protostuff.parser.api.Importer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ParserIT {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("test/test.proto");
        Proto proto = context.getProto();
        assertNotNull(proto);
        assertEquals("test/test.proto", proto.getName());
        assertEquals("proto3", proto.getSyntax());
        assertEquals("test", proto.getPackageName());
        Message testMessage = null;
        for (Message message : proto.getMessages()) {
            if ("test.TestMessage".equals(message.getFullName())) {
                testMessage = message;
            }
        }
        assertNotNull(testMessage);
        assertEquals("TestMessage", testMessage.getName());
        assertTrue(proto == testMessage.getProto());
        System.out.println(proto);
    }
}
