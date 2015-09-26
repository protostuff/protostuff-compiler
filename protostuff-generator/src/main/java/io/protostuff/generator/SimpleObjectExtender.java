package io.protostuff.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class SimpleObjectExtender<ObjectT> implements ObjectExtender<ObjectT> {

    private final Map<String, Function<ObjectT, ?>> propertyProviders;

    public SimpleObjectExtender(Map<String, Function<ObjectT, ?>> propertyProviders) {
        this.propertyProviders = propertyProviders;
    }

    private SimpleObjectExtender(Builder builder) {
        propertyProviders = builder.propertyProviders;
    }

    public static <ObjectT> Builder<ObjectT> newBuilder() {
        return new Builder<ObjectT>();
    }

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


    public static final class Builder<ObjectT> {
        private Map<String, Function<ObjectT, ?>> propertyProviders = new HashMap<>();

        private Builder() {
        }

        public Builder<ObjectT> property(String name, Function<ObjectT, ?> valueProvider) {
            propertyProviders.put(name, valueProvider);
            return this;
        }

        public SimpleObjectExtender<ObjectT> build() {
            return new SimpleObjectExtender<>(this);
        }
    }
}
