package io.protostuff.compiler.parser;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import io.protostuff.compiler.model.Extension;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class LocalExtensionRegistry implements ExtensionRegistry {

    private final SetMultimap<String, Extension> extensions = HashMultimap.create();
    private final ConcurrentMap<String, Map<String, Extension>> mapCache = new ConcurrentHashMap<>();

    @Override
    public void registerExtension(Extension extension) {
        String fullName = extension.getExtendee().getFullName();
        Preconditions.checkNotNull(fullName);
        extensions.put(fullName, extension);
        mapCache.remove(fullName);
    }

    @Override
    public Collection<Extension> getExtensions(String messageName) {
        return extensions.get(messageName);
    }

    @Override
    public Map<String, Extension> getExtensionMap(String messageName) {
        return mapCache.computeIfAbsent(messageName, s -> {
            Map<String, Extension> map = new HashMap<>();
            Collection<Extension> extensions = getExtensions(messageName);
            for (Extension extension : extensions) {
                map.put(extension.getName(), extension);
            }
            return map;
        });
    }
}
