package io.protostuff.compiler.model;

/**
 * Base interface for all protocol buffers types.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface Type {

    String getName();

    String getReference();
}
