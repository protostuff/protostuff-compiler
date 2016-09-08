package io.protostuff.compiler;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface Visitor<T> {

    void visit(T element);
}
