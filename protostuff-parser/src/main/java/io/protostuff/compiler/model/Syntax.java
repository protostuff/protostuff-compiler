package io.protostuff.compiler.model;

import java.util.Objects;

/**
 * Syntax node of a proto file.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public final class Syntax extends AbstractElement {

    public static final Syntax DEFAULT = new Syntax(null, "proto2");

    private final Proto parent;
    private final String value;

    public Syntax(Proto parent, String value) {
        this.parent = parent;
        this.value = value;
    }

    @Override
    public Proto getParent() {
        return parent;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Syntax other = (Syntax) o;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
