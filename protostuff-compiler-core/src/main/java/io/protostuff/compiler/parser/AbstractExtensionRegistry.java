package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractExtensionRegistry implements ExtensionRegistry {

    private final ConcurrentMap<String, Map<String, Field>> fieldCache;

    protected AbstractExtensionRegistry() {
        fieldCache = new ConcurrentHashMap<>();
    }

    @Override
    public void registerExtension(Extension extension) {
        String fullyQualifiedName = extension.getExtendee().getFullyQualifiedName();
        fieldCache.remove(fullyQualifiedName);
    }

    @Override
    public Collection<Extension> getExtensions(Message message) {
        String fullyQualifiedName = message.getFullyQualifiedName();
        return getExtensions(fullyQualifiedName);
    }

    @Override
    public Map<String, Field> getExtensionFields(String fullMessageName) {
        return fieldCache.computeIfAbsent(fullMessageName, messageName -> {
            Map<String, Field> map = new HashMap<>();
            Collection<Extension> extensions = getExtensions(messageName);
            for (Extension extension : extensions) {
                for (Field field : extension.getFields()) {
                    String key = extension.getNamespace() + field.getName();
                    map.put(key, field);
                }
            }
            return map;
        });
    }

    @Override
    public Map<String, Field> getExtensionFields(Message message) {
        String fullyQualifiedName = message.getFullyQualifiedName();
        return getExtensionFields(fullyQualifiedName);
    }
}
