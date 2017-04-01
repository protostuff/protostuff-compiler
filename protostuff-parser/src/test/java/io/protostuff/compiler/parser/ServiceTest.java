package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class ServiceTest extends AbstractParserTest {

    @Test
    public void testSample() throws Exception {
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/services_sample.proto");

        Service service = context.resolve(".protostuff_unittest.SearchService", Service.class);

        assertEquals("SearchService", service.getName());
        assertEquals(1, service.getMethods().size());
        ServiceMethod method = service.getMethods().get(0);

        assertEquals("search", method.getName());
        assertEquals(".protostuff_unittest.SearchRequest", method.getArgType().getFullyQualifiedName());
        assertEquals(".protostuff_unittest.SearchResponse", method.getReturnType().getFullyQualifiedName());
    }

    @Test
    public void badArgumentType() throws Exception {
        String message = "Cannot use 'Enum' as a service method argument type: not a message " +
                "[protostuff_unittest/services_bad_arg_type.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/services_bad_arg_type.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void badReturnType() throws Exception {
        String message = "Cannot use 'Enum' as a service method return type: not a message " +
                "[protostuff_unittest/services_bad_return_type.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/services_bad_return_type.proto");
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void duplicateMethodName() throws Exception {
        String message = "Duplicate service method name: 'action' " +
                "[protostuff_unittest/duplicate_service_rpc_name.proto:7]";
        ParserException exception = assertThrows(ParserException.class, () -> {
            importer.importFile(new ClasspathFileReader(), "protostuff_unittest/duplicate_service_rpc_name.proto");
        });
        assertEquals(message, exception.getMessage());
    }
}
