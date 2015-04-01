package io.protostuff.parser.api;

import io.protostuff.proto3.FileDescriptor;
import org.antlr.v4.runtime.CharStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileDescriptorLoader {

    FileDescriptor parse(String name, CharStream stream);

}
