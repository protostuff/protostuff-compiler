package io.protostuff.compiler.parser;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Singleton
public class ImporterImpl implements Importer {

    private final FileDescriptorLoader loader;

    private Map<String, ProtoContext> cachedImports = new HashMap<String, ProtoContext>();

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
