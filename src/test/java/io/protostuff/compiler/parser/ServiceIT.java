package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceIT {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("test/services/test.proto");

        Service service = context.resolve(".test.services.SearchService", Service.class);

        assertEquals("SearchService", service.getName());
        assertEquals(1, service.getMethods().size());
        ServiceMethod method = service.getMethods().get(0);

        assertEquals("search", method.getName());
        assertEquals(".test.services.SearchRequest", method.getArgType().getReference());
        assertEquals(".test.services.SearchResponse", method.getReturnType().getReference());
    }
}
