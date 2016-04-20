package io.protostuff.generator;

import java.util.function.Function;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ObjectExtender<ObjectT> {

    boolean hasProperty(String propertyName);

    Object getProperty(ObjectT object, String propertyName);

    void register(String property, Function<ObjectT, ?> function);
}
