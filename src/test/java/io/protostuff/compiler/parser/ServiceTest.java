package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceTest extends AbstractParserTest {

    @Test
    public void testSample() throws Exception {
        ProtoContext context = importer.importFile("protostuff_unittest/services_sample.proto");

        Service service = context.resolve(".protostuff_unittest.SearchService", Service.class);

        assertEquals("SearchService", service.getName());
        assertEquals(1, service.getMethods().size());
        ServiceMethod method = service.getMethods().get(0);

        assertEquals("search", method.getName());
        assertEquals(".protostuff_unittest.SearchRequest", method.getArgType().getReference());
        assertEquals(".protostuff_unittest.SearchResponse", method.getReturnType().getReference());
    }

    @Test
    public void badArgumentType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot use 'Enum' as a service method argument type: not a message " +
                "[protostuff_unittest/services_bad_arg_type.proto:7]");
        importer.importFile("protostuff_unittest/services_bad_arg_type.proto");
    }

    @Test
    public void badReturnType() throws Exception {
        thrown.expect(ParserException.class);
        thrown.expectMessage("Cannot use 'Enum' as a service method return type: not a message " +
                "[protostuff_unittest/services_bad_return_type.proto:7]");
        importer.importFile("protostuff_unittest/services_bad_return_type.proto");
    }
}
