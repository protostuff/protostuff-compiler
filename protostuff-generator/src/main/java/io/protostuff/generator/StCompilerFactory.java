package io.protostuff.generator;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface StCompilerFactory {

    ProtoCompiler create(String template);

}
