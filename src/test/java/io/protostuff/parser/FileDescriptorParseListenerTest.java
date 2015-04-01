package io.protostuff.parser;

import com.google.common.base.Joiner;
import io.protostuff.proto3.*;
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
public class FileDescriptorParseListenerTest {

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
        FileDescriptor fileDescriptor = parseProto("");
        Assert.assertNull(fileDescriptor.getSyntax());
        Assert.assertNull(fileDescriptor.getPackageName());
        assertEquals(0, fileDescriptor.getImports().size());
    }

    @Test
    public void parseSyntaxPackageImports() throws Exception {
        FileDescriptor fileDescriptor = parseProto(PROTO_WITH_SYNTAX_PACKAGE_IMPORTS);
        assertEquals("proto3", fileDescriptor.getSyntax());
        assertEquals("pt.test", fileDescriptor.getPackageName());
        assertEquals("foo.proto", fileDescriptor.getImports().get(0));
        assertEquals("bar/baz.proto", fileDescriptor.getImports().get(1));
    }

    @Test
    public void parseOptions() throws Exception {
        FileDescriptor fileDescriptor = parseProto(PROTO_WITH_OPTIONS);
        assertEquals("io.protostuff.test", fileDescriptor.getStandardOptions().get("java_package"));
        assertTrue(fileDescriptor.getCustomOptions().get("google.protobuf.objectivec_file_options") instanceof Map);
    }

    private FileDescriptor parseProto(String input) {
        CharStream stream = new ANTLRInputStream(input);
        Proto3Lexer lexer = new Proto3Lexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(TestUtils.ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Proto3Parser parser = new Proto3Parser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(TestUtils.ERROR_LISTENER);
        Context context = new Context();
        ProtoParseListener protoParseListener = new ProtoParseListener(context);
        OptionParseListener optionParseListener = new OptionParseListener(context);
        parser.addParseListener(protoParseListener);
        parser.addParseListener(optionParseListener);
        parser.proto();
        return context.getResult();
    }
}
