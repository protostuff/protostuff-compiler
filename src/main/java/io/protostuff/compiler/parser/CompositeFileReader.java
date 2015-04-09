package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.CharStream;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
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
