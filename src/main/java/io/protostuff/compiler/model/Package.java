package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Package extends AbstractElement {

    public static final Package DEFAULT = new Package("");

    private final String value;

    public Package(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return Objects.equals(value, aPackage.value);
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
