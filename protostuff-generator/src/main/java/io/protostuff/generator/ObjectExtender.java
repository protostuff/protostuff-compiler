package io.protostuff.generator;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ObjectExtender<ObjectT> {

    boolean hasProperty(String propertyName);

    Object getProperty(ObjectT object, String propertyName);
}
