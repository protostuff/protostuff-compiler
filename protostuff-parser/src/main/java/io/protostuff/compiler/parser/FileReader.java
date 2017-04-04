package io.protostuff.compiler.parser;

import javax.annotation.Nullable;
import org.antlr.v4.runtime.CharStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileReader {

    @Nullable
    CharStream read(String name);
}
