package io.protostuff.compiler.parser;

/**
 * Proto file importer - parses and returns context for a proto file.
 * Results are cached.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface Importer {

    ProtoContext importFile(FileReader fileReader, String fileName);
}
