package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import java.util.Collection;
import java.util.Map;

/**
 * Extension registry. Used in two phases: first, we register all
 * extensions; second - when we know all extensions for the message,
 * we can validate options.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensionRegistry {

    void registerExtension(Extension extension);

    Collection<Extension> getExtensions(Message message);

    Collection<Extension> getExtensions(String messageName);

    Map<String, Field> getExtensionFields(String messageName);

    Map<String, Field> getExtensionFields(Message message);
}
