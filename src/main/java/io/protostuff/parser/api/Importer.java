package io.protostuff.parser.api;

import io.protostuff.proto3.FileDescriptor;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface Importer {

    FileDescriptor importFile(String fileName);
}
