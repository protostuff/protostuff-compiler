package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
