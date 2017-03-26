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
class JsonMessageGeneratorTest {

    private Injector injector;
    private Importer importer;
    private JsonMessageGenerator generator;
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
        generator = new JsonMessageGenerator(null, markdownProcessor) {
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
        Assertions.assertEquals(4, json.size());
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("A")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("a")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.REPEATED)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(0));
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("B")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A.B")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("b")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(1));
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("C")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A.B.C")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("c")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(2));
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("D")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A.B.C.D")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("d")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(3));
    }


    @Test
    void fieldModifiers() {
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/field_modifiers.proto");
        Module module = ImmutableModule.builder()
                .name("name")
                .output("output")
                .addProtos(context.getProto())
                .usageIndex(usageIndex)
                .build();
        generator.compile(module);
        Assertions.assertEquals(1, json.size());
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("A")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("optional")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .addFields(ImmutableMessageField.builder()
                        .name("required")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.REQUIRED)
                        .tag(2)
                        .map(false)
                        .description("")
                        .build())
                .addFields(ImmutableMessageField.builder()
                        .name("repeated")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.REPEATED)
                        .tag(3)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(0));
    }

}