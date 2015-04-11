package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.CompilerModule;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.generator.ProtoCompiler;
import io.protostuff.compiler.generator.StCompiler;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
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
        injector = Guice.createInjector(
                new ParserModule(),
                new CompilerModule("io/protostuff/compiler/proto/proto3.stg"));
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

        ProtoCompiler compiler = injector.getInstance(ProtoCompiler.class);
        compiler.compile(proto);
    }
}
