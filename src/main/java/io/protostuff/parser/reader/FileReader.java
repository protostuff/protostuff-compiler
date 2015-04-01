package io.protostuff.parser.reader;

import org.antlr.v4.runtime.CharStream;

import javax.annotation.Nullable;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileReader {

    @Nullable
    CharStream read(String name);
}
