package io.protostuff.generator;

import java.util.function.Function;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface PropertyProvider<T> {

    boolean hasProperty(String propertyName);

    Object getProperty(T object, String propertyName);

    void register(String property, Function<T, ?> function);
}
