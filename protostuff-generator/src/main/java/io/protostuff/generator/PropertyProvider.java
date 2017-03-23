package io.protostuff.generator;

import java.util.function.Function;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface PropertyProvider {

    boolean hasProperty(String propertyName);

    Object getProperty(Object object, String propertyName);

    void register(String property, Function<?, Object> computable);
}
