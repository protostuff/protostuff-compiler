package io.protostuff.generator;

import io.protostuff.compiler.model.Module;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ProtoCompiler {

    String getName();

    void compile(Module module);
}
