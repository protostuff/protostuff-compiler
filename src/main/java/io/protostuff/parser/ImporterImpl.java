package io.protostuff.parser;

import io.protostuff.parser.api.FileDescriptorLoader;
import io.protostuff.parser.api.Importer;
import io.protostuff.parser.reader.FileReader;
import org.antlr.v4.runtime.CharStream;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Singleton
public class ImporterImpl implements Importer {

    private final FileReader reader;
    private final FileDescriptorLoader loader;

    @Inject
    public ImporterImpl(FileReader reader, FileDescriptorLoader loader) {
        this.reader = reader;
        this.loader = loader;
    }

    @Override
    public ProtoContext importFile(String fileName) {

        CharStream stream = reader.read(fileName);
        ProtoContext context = loader.load(fileName, stream);
        return context;
    }

}
