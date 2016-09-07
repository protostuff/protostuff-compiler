package io.protostuff.compiler.parser;

import com.google.inject.assistedinject.Assisted;
import org.antlr.v4.runtime.CharStream;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.File;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoFileReader implements FileReader {

    private final List<File> includePathList;
    private final FileReader delegate;

    @Inject
    public ProtoFileReader(@Assisted List<File> includePathList) {
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
