package io.protostuff.compiler.parser;

/**
 * Loader of proto files. Returns fully parsed proto file context
 * for a given proto file.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileDescriptorLoader {

    ProtoContext load(FileReader reader, String filename);

}
