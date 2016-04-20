package io.protostuff.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class SimpleObjectExtender<ObjectT> implements ObjectExtender<ObjectT> {

    private final Map<String, Function<ObjectT, ?>> propertyProviders = new HashMap<>();

    @Override
    public boolean hasProperty(String propertyName) {
        return propertyProviders.containsKey(propertyName);
    }

    @Override
    public Object getProperty(ObjectT object, String propertyName) {
        Function<ObjectT, ?> provider = propertyProviders.get(propertyName);
        if (provider == null) {
            throw new IllegalArgumentException(propertyName);
        }
        return provider.apply(object);
    }

    @Override
    public void register(String property, Function<ObjectT, ?> function) {
        propertyProviders.put(property, function);
    }

}
