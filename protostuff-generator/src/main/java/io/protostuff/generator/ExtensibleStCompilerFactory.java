package io.protostuff.generator;

import java.util.Collection;

/**
 * StringTemplate-based engines factory.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensibleStCompilerFactory {

    ProtoCompiler create(Collection<String> templates, ExtensionProvider extensionProvider);
}
