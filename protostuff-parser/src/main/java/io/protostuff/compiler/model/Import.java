package io.protostuff.compiler.model;

import java.util.Objects;

/**
 * Import node of a proto file.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public final class Import extends AbstractElement {

    private final Proto parent;
    private final String value;
    private final boolean isPublic;
    private Proto proto;

    /**
     * Create new import node instance.
     */
    public Import(Proto parent, String value, boolean isPublic) {
        this.parent = parent;
        this.value = value;
        this.isPublic = isPublic;
    }

    @Override
    public Proto getParent() {
        return parent;
    }

    public String getValue() {
        return value;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Proto getProto() {
        return proto;
    }

    public void setProto(Proto proto) {
        this.proto = proto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Import that = (Import) o;
        return Objects.equals(isPublic, that.isPublic)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, isPublic);
    }

    @Override
    public String toString() {
        return value;
    }
}
