package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.Proto;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ExtensionsIT {
    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("test/extensions/test.proto");
        System.out.println(context.getProto());
        Proto proto = context.getProto();
        Extension extension = proto.getExtension("y");
    }

}
