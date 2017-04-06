package io.protostuff.compiler.parser;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.antlr.v4.runtime.CharStream;

/**
 * File reader that can read data from multiple sources -
 * classpath, local file system, etc.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class CompositeFileReader implements FileReader {

    private final List<FileReader> delegateList;

    public CompositeFileReader(List<FileReader> delegateList) {
        this.delegateList = delegateList;
    }

    public CompositeFileReader(FileReader... delegates) {
        this.delegateList = Arrays.asList(delegates);
    }

    @Nullable
    @Override
    public CharStream read(String name) {
        for (FileReader delegate : delegateList) {
            CharStream result = delegate.read(name);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
