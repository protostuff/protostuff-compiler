package io.protostuff.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.parser.api.Importer;
import io.protostuff.proto3.FileDescriptor;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ParserIT {

    @Test
    public void testName() throws Exception {
        Injector injector = Guice.createInjector(new ParserModule());
        Importer importer = injector.getInstance(Importer.class);
        FileDescriptor descriptor = importer.importFile("test/test.proto");
    }
}
