package io.protostuff.compiler.parser;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface Importer {

    ProtoContext importFile(String fileName);
}
