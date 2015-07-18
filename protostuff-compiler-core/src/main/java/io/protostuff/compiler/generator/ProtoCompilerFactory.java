package io.protostuff.compiler.generator;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ProtoCompilerFactory {

    ProtoCompiler create(String template);
}
