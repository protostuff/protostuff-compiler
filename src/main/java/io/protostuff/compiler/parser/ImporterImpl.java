package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.CharStream;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Singleton
public class ImporterImpl implements Importer {

    private final FileReader reader;
    private final FileDescriptorLoader loader;

    private Map<String, ProtoContext> cachedImports = new HashMap<>();

    @Inject
    public ImporterImpl(FileReader reader, FileDescriptorLoader loader) {
        this.reader = reader;
        this.loader = loader;
    }

    @Override
    public ProtoContext importFile(String fileName) {
        ProtoContext cachedInstance = cachedImports.get(fileName);
        if (cachedInstance != null) {
            if (cachedInstance.isInitialized()) {
                return cachedInstance;
            }
            throw new ParserException("Can not load proto: imports cycle found");
        }
        CharStream stream = reader.read(fileName);
        if (stream == null) {
            throw new ParserException("Can not load proto: %s not found", fileName);
        }
        ProtoContext context = loader.load(fileName, stream);
        cachedImports.put(fileName, context);
        return context;
    }

}
