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
        fieldCache = new ConcurrentHashMap<String, Map<String, Field>>();
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
        if (fieldCache.containsKey(fullMessageName)) {
            return fieldCache.get(fullMessageName);
        } else {
            Map<String, Field> map = new HashMap<String, Field>();
            Collection<Extension> extensions = getExtensions(fullMessageName);
            for (Extension extension : extensions) {
                for (Field field : extension.getFields()) {
                    String key = extension.getNamespace() + field.getName();
                    map.put(key, field);
                }
            }
            fieldCache.put(fullMessageName, map);
            return map;
        }
    }

    @Override
    public Map<String, Field> getExtensionFields(Message message) {
        String fullyQualifiedName = message.getFullyQualifiedName();
        return getExtensionFields(fullyQualifiedName);
    }
}
