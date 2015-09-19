package io.protostuff.compiler.parser;

import java.util.Collection;
import java.util.Map;

import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensionRegistry {

    void registerExtension(Extension extension);

    Collection<Extension> getExtensions(Message message);

    Collection<Extension> getExtensions(String messageName);

    Map<String, Field> getExtensionFields(String messageName);

    Map<String, Field> getExtensionFields(Message message);
}
