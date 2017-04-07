package io.protostuff.generator;

/**
 * StringTemplate-based engine compilers factory.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface StCompilerFactory {

    ProtoCompiler create(String template);

}
