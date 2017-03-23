package io.protostuff.generator;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class AbstractExtensionProvider implements ExtensionProvider {

    private final Map<Class<?>, AttributeRenderer> attributeRenderers;

    private final Map<Class<?>, PropertyProvider> extenderMap;

    public AbstractExtensionProvider() {
        extenderMap = new HashMap<>();
        attributeRenderers = new HashMap<>();
    }

    public final <T> void registerProperty(Class<T> object, String property, Function<T, Object> function) {
        PropertyProvider extender = extenderMap.computeIfAbsent(object,
                aClass -> new SimplePropertyProvider());
        extender.register(property, function);
    }

    @Override
    public Map<Class<?>, AttributeRenderer> attributeRenderers() {
        return Collections.unmodifiableMap(attributeRenderers);
    }

    @Override
    public Map<Class<?>, PropertyProvider> propertyProviders() {
        return Collections.unmodifiableMap(extenderMap);
    }
}
