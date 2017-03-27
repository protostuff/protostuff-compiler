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
import io.protostuff.generator.ProtoCompiler;
import io.protostuff.generator.html.json.enumeration.JsonEnumGenerator;
import io.protostuff.generator.html.markdown.MarkdownProcessor;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractJsonGeneratorTest {
    Injector injector;
    Importer importer;
    MarkdownProcessor markdownProcessor;
    UsageIndex usageIndex;
    JsonEnumGenerator enumGenerator;
    JsonMessageGenerator messageGenerator;

    List<Object> json;

    @BeforeEach
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
        importer = injector.getInstance(Importer.class);
        markdownProcessor = source -> source;
        usageIndex = new UsageIndex();
        json = new ArrayList<>();
        enumGenerator = new JsonEnumGenerator(null, markdownProcessor) {
            @Override
            protected void write(Module module, String file, Object data) {
                json.add(data);
            }
        };
        messageGenerator = new JsonMessageGenerator(null, markdownProcessor) {
            @Override
            protected void write(Module module, String file, Object data) {
                json.add(data);
            }
        };
    }

    void compile(ProtoCompiler generator, String fileName) {
        ProtoContext context = importer.importFile(new ClasspathFileReader(), fileName);
        usageIndex = UsageIndex.build(context.getProto());
        Module module = ImmutableModule.builder()
                .name("name")
                .output("output")
                .addProtos(context.getProto())
                .usageIndex(usageIndex)
                .build();
        generator.compile(module);
    }

}
