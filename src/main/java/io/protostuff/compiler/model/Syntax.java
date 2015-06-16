package io.protostuff.compiler.model;

import java.util.Objects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public final class Syntax extends AbstractElement {

    private final String value;

    public Syntax(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
