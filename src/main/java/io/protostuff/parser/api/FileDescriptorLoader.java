package io.protostuff.parser.api;

import io.protostuff.parser.ProtoContext;
import org.antlr.v4.runtime.CharStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileDescriptorLoader {

    ProtoContext load(String name, CharStream stream);

}
