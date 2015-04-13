package io.protostuff.compiler.parser;

import com.google.common.base.Joiner;
import io.protostuff.compiler.model.Proto;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoParseListenerTest {

    public static final String PROTO_WITH_SYNTAX_PACKAGE_IMPORTS = Joiner.on('\n').join(
            "syntax = \"proto3\";",
            "package pt.test;",
            "import \"foo.proto\";",
            "import \"bar/baz.proto\";"
    );

    public static final String PROTO_WITH_OPTIONS = Joiner.on('\n').join(
            "option java_package = \"io.protostuff.test\";",
            "option (google.protobuf.objectivec_file_options).class_prefix = \"LV2\";"
    );

    @Test
    public void parseEmptyFile() throws Exception {
        Proto proto = parseProto("");
        Assert.assertNull(proto.getSyntax());
        Assert.assertNull(proto.getPackageName());
        assertEquals(0, proto.getImports().size());
    }

    @Test
    public void parseSyntaxPackageImports() throws Exception {
        Proto proto = parseProto(PROTO_WITH_SYNTAX_PACKAGE_IMPORTS);
        assertEquals("proto3", proto.getSyntax());
        assertEquals("pt.test", proto.getPackageName());
        assertEquals("foo.proto", proto.getImports().get(0));
        assertEquals("bar/baz.proto", proto.getImports().get(1));
    }

    @Test
    public void parseOptions() throws Exception {
        Proto proto = parseProto(PROTO_WITH_OPTIONS);
        assertEquals("io.protostuff.test", proto.getOptions().get("java_package"));
        assertTrue(proto.getOptions().get("google.protobuf.objectivec_file_options") instanceof Map);
    }

    private Proto parseProto(String input) {
        CharStream stream = new ANTLRInputStream(input);
        ProtoLexer lexer = new ProtoLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(TestUtils.ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ProtoParser parser = new ProtoParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(TestUtils.ERROR_LISTENER);
        ProtoContext context = new ProtoContext("test.proto");
        ProtoParseListener protoParseListener = new ProtoParseListener(context);
        OptionParseListener optionParseListener = new OptionParseListener(context);
        parser.addParseListener(protoParseListener);
        parser.addParseListener(optionParseListener);
        parser.proto();
        return context.getProto();
    }
}
