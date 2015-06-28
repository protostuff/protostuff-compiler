package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class GroupsIT {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("protostuff_unittest/groups_sample.proto");
    }
}
