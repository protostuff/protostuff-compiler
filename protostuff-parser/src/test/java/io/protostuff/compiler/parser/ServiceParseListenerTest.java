package io.protostuff.compiler.parser;

import com.google.common.base.Joiner;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceParseListenerTest {

    @Test
    public void serviceName() throws Exception {
        String input = Joiner.on('\n').join(
                "service A {",
                "}"
        );
        Service service = parseService(input);
        assertEquals("A", service.getName());
    }

    @Test
    public void methodRequestResponseType() throws Exception {
        String input = Joiner.on('\n').join(
                "service A {",
                "  rpc test (req) returns (resp);",
                "}"
        );
        Service service = parseService(input);
        ServiceMethod method = service.getMethod("test");
        assertNotNull(method);
        Assert.assertEquals("test", method.getName());
        Assert.assertEquals("req", method.getArgTypeName());
        Assert.assertEquals("resp", method.getReturnTypeName());
    }

    @Test
    public void requestIsStream() throws Exception {
        String input = Joiner.on('\n').join(
                "service A {",
                "  rpc test (stream req) returns (resp);",
                "}"
        );
        Service service = parseService(input);
        ServiceMethod method = service.getMethod("test");
        Assert.assertTrue(method.isArgStream());
        Assert.assertFalse(method.isReturnStream());
    }

    @Test
    public void returnIsStream() throws Exception {
        String input = Joiner.on('\n').join(
                "service A {",
                "  rpc test (req) returns (stream resp);",
                "}"
        );
        Service service = parseService(input);
        ServiceMethod method = service.getMethod("test");
        Assert.assertFalse(method.isArgStream());
        Assert.assertTrue(method.isReturnStream());
    }

    private Service parseService(String input) {
        CharStream stream = new ANTLRInputStream(input);
        ProtoLexer lexer = new ProtoLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(TestUtils.ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ProtoParser parser = new ProtoParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(TestUtils.ERROR_LISTENER);
        ProtoContext context = new ProtoContext("test.proto");
        ServiceParseListener serviceParseListener = new ServiceParseListener(tokenStream, context);
        OptionParseListener optionParseListener = new OptionParseListener(tokenStream, context);
        Proto proto = new Proto();
        context.push(proto);
        parser.addParseListener(serviceParseListener);
        parser.addParseListener(optionParseListener);
        parser.serviceBlock();
        return proto.getServices().get(0);
    }
}