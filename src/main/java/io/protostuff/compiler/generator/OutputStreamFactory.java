package io.protostuff.compiler.generator;

import java.io.OutputStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface OutputStreamFactory {

    /**
     * Create an output stream for file location.
     * String that represents file location should be relative path, '/' is a delimiter.
     *
     * @param location sting that contains relative file location
     * @return new stream instance
     */
    OutputStream createStream(String location);
}
