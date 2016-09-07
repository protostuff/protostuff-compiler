package io.protostuff.generator;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class AbstractExtensionProvider implements ExtensionProvider {

    private final Map<Class<?>, AttributeRenderer> attributeRenderers;

    private final Map<Class<?>, PropertyProvider<?>> extenderMap;

    public AbstractExtensionProvider() {
        attributeRenderers = new HashMap<Class<?>, AttributeRenderer>();
        extenderMap = new HashMap<Class<?>, PropertyProvider<?>>();
    }

    @SuppressWarnings("unchecked")
    public final <T> void registerProperty(Class<T> object, String property, ComputableProperty<T, ?> function) {
        PropertyProvider<T> extender;
        if (extenderMap.containsKey(object)) {
            extender = (PropertyProvider<T>) extenderMap.get(object);
        } else {
            extender = new SimplePropertyProvider<T>();
            extenderMap.put(object, extender);
        }
        extender.register(property, function);
    }

    @Override
    public Map<Class<?>, AttributeRenderer> attributeRenderers() {
        return Collections.unmodifiableMap(attributeRenderers);
    }

    @Override
    public Map<Class<?>, PropertyProvider<?>> propertyProviders() {
        return Collections.unmodifiableMap(extenderMap);
    }
}
