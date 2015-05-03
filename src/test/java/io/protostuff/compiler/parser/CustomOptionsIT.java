package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Extension;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CustomOptionsIT {
    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("protobuf_unittest/unittest_custom_options.proto");
        System.out.println(context.getProto());
    }

}
