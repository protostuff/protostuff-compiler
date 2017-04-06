package io.protostuff.compiler.parser;

import com.google.common.base.MoreObjects;
import com.google.inject.assistedinject.Assisted;
import java.nio.file.Path;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.antlr.v4.runtime.CharStream;

/**
 * File reader implementation that can do file lookup in multiple folders.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class MultiPathFileReader implements FileReader {

    private final List<Path> includePathList;
    private final FileReader delegate;

    /**
     * Create new instance for a specified list of lookup paths.
     */
    @Inject
    public MultiPathFileReader(@Assisted List<Path> includePathList) {
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("includePathList", includePathList)
                .add("delegate", delegate)
                .toString();
    }
}
