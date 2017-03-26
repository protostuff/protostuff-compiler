package io.protostuff.generator.html.json.message;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.ImmutableModule;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.UsageIndex;
import io.protostuff.compiler.parser.ClasspathFileReader;
import io.protostuff.compiler.parser.Importer;
import io.protostuff.compiler.parser.ProtoContext;
import io.protostuff.generator.html.json.enumeration.ImmutableEnumConstant;
import io.protostuff.generator.html.json.enumeration.ImmutableEnumDescriptor;
import io.protostuff.generator.html.json.enumeration.JsonEnumGenerator;
import io.protostuff.generator.html.json.index.NodeType;
import io.protostuff.generator.html.markdown.MarkdownProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class JsonEnumGeneratorTest {

    private Injector injector;
    private Importer importer;
    private JsonEnumGenerator generator;
    private MarkdownProcessor markdownProcessor;
    private UsageIndex usageIndex;

    private List<Object> json;

    @BeforeEach
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
        importer = injector.getInstance(Importer.class);
        markdownProcessor = source -> source;
        usageIndex = new UsageIndex();
        json = new ArrayList<>();
        generator = new JsonEnumGenerator(null, markdownProcessor) {
            @Override
            protected void write(Module module, String file, Object data) {
                json.add(data);
            }
        };
    }

    @Test
    void compile() {
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/messages_sample.proto");
        Module module = ImmutableModule.builder()
                .name("name")
                .output("output")
                .addProtos(context.getProto())
                .usageIndex(usageIndex)
                .build();
        generator.compile(module);
        Assertions.assertEquals(2, json.size());
        Assertions.assertEquals(ImmutableEnumDescriptor.builder()
                .name("E")
                .type(NodeType.ENUM)
                .canonicalName("protostuff_unittest.E")
                .description("")
                .addConstants(ImmutableEnumConstant.builder()
                        .name("X")
                        .description("")
                        .value(0)
                        .build())
                .build(), json.get(0));
        Assertions.assertEquals(ImmutableEnumDescriptor.builder()
                .name("E")
                .type(NodeType.ENUM)
                .canonicalName("protostuff_unittest.A.B.C.D.E")
                .description("")
                .addConstants(ImmutableEnumConstant.builder()
                        .name("X")
                        .description("")
                        .value(0)
                        .build())
                .build(), json.get(1));
    }

}