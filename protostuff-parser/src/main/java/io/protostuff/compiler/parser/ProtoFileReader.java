package io.protostuff.compiler.parser;

import com.google.inject.assistedinject.Assisted;

import org.antlr.v4.runtime.CharStream;

import java.nio.file.Path;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoFileReader implements FileReader {

    private final List<Path> includePathList;
    private final FileReader delegate;

    @Inject
    public ProtoFileReader(@Assisted List<Path> includePathList) {
        this.includePathList = includePathList;
        ClasspathFileReader classpathFileReader = new ClasspathFileReader();
        LocalFileReader localFileReader = new LocalFileReader(includePathList);
        delegate = new CompositeFileReader(localFileReader, classpathFileReader);
    }

    @Nullable
    @Override
    public CharStream read(String name) {
        return delegate.read(name);
    }
}
