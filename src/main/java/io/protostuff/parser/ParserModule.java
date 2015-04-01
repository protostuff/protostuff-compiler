package io.protostuff.parser;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.protostuff.parser.api.FileDescriptorLoader;
import io.protostuff.parser.api.Importer;
import io.protostuff.parser.reader.ClasspathFileReader;
import io.protostuff.parser.reader.CompositeFileReader;
import io.protostuff.parser.reader.FileReader;
import io.protostuff.parser.reader.LocalFileReader;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.BailErrorStrategy;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ParserModule extends AbstractModule {

    private final List<Path> protoIncludePathList;

    public ParserModule() {
        this.protoIncludePathList = Collections.emptyList();
    }

    public ParserModule(List<Path> protoIncludePathList) {
        this.protoIncludePathList = protoIncludePathList;
    }

    @Override
    protected void configure() {
        bind(Importer.class).to(ImporterImpl.class);
        bind(FileDescriptorLoader.class).to(FileDescriptorLoaderImpl.class);
        bind(ANTLRErrorListener.class).to(ParseErrorLogger.class);
        bind(ANTLRErrorStrategy.class).to(BailErrorStrategy.class);
    }

    @Provides
    Proto3Listener listener() {
            return null;
    }

    @Provides
    FileReader fileReader() {
        ClasspathFileReader classpathFileReader = new ClasspathFileReader();
        LocalFileReader localFileReader = new LocalFileReader(protoIncludePathList);
        return new CompositeFileReader(localFileReader, classpathFileReader);
    }
}
