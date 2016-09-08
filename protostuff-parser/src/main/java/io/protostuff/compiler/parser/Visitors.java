package io.protostuff.compiler.parser;

import io.protostuff.compiler.Visitor;

import java.util.Collection;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Visitors {

    private Visitors() {
    }

    public static <T> void run(Collection<? extends T> collection, Visitor<T> visitor) {
        for (T element : collection) {
            visitor.visit(element);
        }
    }
}
