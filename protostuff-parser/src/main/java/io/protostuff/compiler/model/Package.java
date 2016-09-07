package io.protostuff.compiler.model;

import com.google.common.base.Objects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Package extends AbstractElement {

    public static final Package DEFAULT = new Package(null, "");

    private final Proto parent;
    private final String value;

    public Package(Proto parent, String value) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return Objects.equal(value, aPackage.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
