package io.protostuff.compiler.model;

import java.util.Objects;

/**
 * Tree element for "package" node of proto file.
 *
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Package that = (Package) o;
        return Objects.equals(value, that.value);
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
