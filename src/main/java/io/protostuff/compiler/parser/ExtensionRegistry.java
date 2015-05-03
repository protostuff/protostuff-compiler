package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Extension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensionRegistry {

    void registerExtension(Extension extension);

    Collection<Extension> getExtensions(String messageName);

    Map<String, Extension> getExtensionMap(String messageName);
}
