package io.protostuff.parser.api;

import io.protostuff.parser.ProtoContext;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface Importer {

    ProtoContext importFile(String fileName);
}
