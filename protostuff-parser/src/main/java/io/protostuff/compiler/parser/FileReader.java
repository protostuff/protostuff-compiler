package io.protostuff.compiler.parser;

import javax.annotation.Nullable;
import org.antlr.v4.runtime.CharStream;

/**
 * File reader reads a file and returns a character stream,
 * later consumed by lexer.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileReader {

    @Nullable
    CharStream read(String name);
}
