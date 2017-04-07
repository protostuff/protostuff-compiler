package io.protostuff.generator;

import io.protostuff.compiler.model.Module;

/**
 * Base interface for all proto generators.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface ProtoCompiler {

    void compile(Module module);
}
