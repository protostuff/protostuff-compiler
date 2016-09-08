package io.protostuff.generator;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ComputableProperty<T, R> {

    R compute(T object);
}
