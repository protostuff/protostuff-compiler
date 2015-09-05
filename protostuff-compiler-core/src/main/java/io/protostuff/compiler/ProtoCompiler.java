package io.protostuff.compiler;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import io.protostuff.compiler.generator.CompilerRegistry;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.ModuleConfiguration;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.parser.FileReader;
import io.protostuff.compiler.parser.FileReaderFactory;
import io.protostuff.compiler.parser.Importer;
import io.protostuff.compiler.parser.ProtoContext;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoCompiler.class);

    private final Injector injector;

    public ProtoCompiler() {
        injector = Guice.createInjector(
                new ParserModule(),
                new CompilerModule());
    }

    public void compile(ModuleConfiguration configuration) {
        FileReaderFactory fileReaderFactory = injector.getInstance(FileReaderFactory.class);
        Importer importer = injector.getInstance(Importer.class);
        CompilerRegistry registry = injector.getInstance(CompilerRegistry.class);
        io.protostuff.compiler.generator.ProtoCompiler compiler = registry.findCompiler(configuration.getTemplate());
        if (compiler == null) {
            LOGGER.error("Unknown template: {}", configuration.getTemplate());
            return;
        }
        FileReader fileReader = fileReaderFactory.create(configuration.getIncludePaths());
        Map<String, Proto> importedFiles = new HashMap<>();
        for (String path : configuration.getProtoFiles()) {
            LOGGER.info("Parse {}", path);
            ProtoContext context = importer.importFile(fileReader, path);
            Proto proto = context.getProto();
            importedFiles.put(path, proto);
        }
        Module module = new Module();
        module.setName(configuration.getName());
        module.setOutput(configuration.getOutput());
        for (Map.Entry<String, Proto> entry : importedFiles.entrySet()) {
            module.addProto(entry.getValue());
        }
        compiler.compile(module);
    }
}
