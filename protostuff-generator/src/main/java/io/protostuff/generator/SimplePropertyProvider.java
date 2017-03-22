package io.protostuff.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class SimplePropertyProvider<T> implements PropertyProvider<T> {

    private final Map<String, Function<T, ?>> propertyProviders = new HashMap<>();

    @Override
    public boolean hasProperty(String propertyName) {
        return propertyProviders.containsKey(propertyName);
    }

    @Override
    public Object getProperty(T object, String propertyName) {
        Function<T, ?> provider = propertyProviders.get(propertyName);
        if (provider == null) {
            throw new IllegalArgumentException(propertyName);
        }
        return provider.apply(object);
    }

    @Override
    public void register(String property, Function<T, ?> function) {
        propertyProviders.put(property, function);
    }

}
