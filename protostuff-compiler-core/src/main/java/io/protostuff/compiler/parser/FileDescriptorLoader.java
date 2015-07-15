package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.CharStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileDescriptorLoader {

    ProtoContext load(String name, CharStream stream);

}
