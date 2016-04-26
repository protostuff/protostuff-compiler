package io.protostuff.generator;

import java.util.function.Function;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface PropertyProvider<ObjectT> {

    boolean hasProperty(String propertyName);

    Object getProperty(ObjectT object, String propertyName);

    void register(String property, Function<ObjectT, ?> function);
}
