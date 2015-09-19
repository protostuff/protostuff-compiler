package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Message;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class GoogleDescriptorProtoParserTest {
    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "google/protobuf/descriptor.proto");
        Message a = context.getProto().getMessage("FileOptions");
        Assert.assertNotNull(a);
    }
}
