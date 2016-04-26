package io.protostuff.generator;

import io.protostuff.compiler.model.Module;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ProtoCompiler {

    void compile(Module module);
}
