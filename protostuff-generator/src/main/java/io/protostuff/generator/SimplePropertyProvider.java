package io.protostuff.generator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class SimplePropertyProvider<ObjectT> implements PropertyProvider<ObjectT> {

    private final Map<String, ComputableProperty<ObjectT, ?>> propertyProviders = new HashMap<String, ComputableProperty<ObjectT, ?>>();

    @Override
    public boolean hasProperty(String propertyName) {
        return propertyProviders.containsKey(propertyName);
    }

    @Override
    public Object getProperty(ObjectT object, String propertyName) {
        ComputableProperty<ObjectT, ?> provider = propertyProviders.get(propertyName);
        if (provider == null) {
            throw new IllegalArgumentException(propertyName);
        }
        return provider.compute(object);
    }

    @Override
    public void register(String property, ComputableProperty<ObjectT, ?> function) {
        propertyProviders.put(property, function);
    }

}
