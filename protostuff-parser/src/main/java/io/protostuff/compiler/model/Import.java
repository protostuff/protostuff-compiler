package io.protostuff.compiler.model;

import com.google.common.base.Objects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public final class Import extends AbstractElement {

    private final Proto parent;
    private final String value;
    private final boolean aPublic;
    private Proto proto;

    public Import(Proto parent, String value, boolean aPublic) {
        this.parent = parent;
        this.value = value;
        this.aPublic = aPublic;
    }

    @Override
    public Proto getParent() {
        return parent;
    }

    public String getValue() {
        return value;
    }

    public boolean isPublic() {
        return aPublic;
    }

    public Proto getProto() {
        return proto;
    }

    public void setProto(Proto proto) {
        this.proto = proto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Import anImport = (Import) o;
        return Objects.equal(aPublic, anImport.aPublic) &&
                Objects.equal(value, anImport.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, aPublic);
    }

    @Override
    public String toString() {
        return value;
    }
}
