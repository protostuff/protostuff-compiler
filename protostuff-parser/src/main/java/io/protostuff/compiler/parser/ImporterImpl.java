package io.protostuff.compiler.parser;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Singleton
public class ImporterImpl implements Importer {

    private final FileDescriptorLoader loader;

    private Map<String, ProtoContext> cachedImports = new HashMap<>();

    @Inject
    public ImporterImpl(FileDescriptorLoader loader) {
        this.loader = loader;
    }

    @Override
    public ProtoContext importFile(FileReader reader, String fileName) {
        ProtoContext cachedInstance = cachedImports.get(fileName);
        if (cachedInstance != null) {
            if (cachedInstance.isInitialized()) {
                return cachedInstance;
            }
            throw new ParserException("Can not load proto: imports cycle found");
        }
        ProtoContext context = loader.load(reader, fileName);
        cachedImports.put(fileName, context);
        return context;
    }

}
