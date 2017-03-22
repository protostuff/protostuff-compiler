package io.protostuff.generator;

import java.util.Collection;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensibleStCompilerFactory {

    ProtoCompiler create(Collection<String> templates, ExtensionProvider extensionProvider);
}
