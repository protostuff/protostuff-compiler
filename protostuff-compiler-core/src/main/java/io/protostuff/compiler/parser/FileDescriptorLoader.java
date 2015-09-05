package io.protostuff.compiler.parser;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileDescriptorLoader {

    ProtoContext load(FileReader reader, String filename);

}
