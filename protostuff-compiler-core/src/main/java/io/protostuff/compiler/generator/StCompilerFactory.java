package io.protostuff.compiler.generator;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface StCompilerFactory {

    ProtoCompiler create(String template);
}
