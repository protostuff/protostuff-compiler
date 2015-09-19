package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Before;
import org.junit.Test;

import io.protostuff.compiler.ParserModule;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class GroupsTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/groups_sample.proto");
    }
}
