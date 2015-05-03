package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class AbstractUserFieldType extends AbstractDescriptor implements UserFieldType {

    protected Proto proto;
    protected String fullName;
    protected boolean nested;
    protected UserTypeContainer parent;

    @Override
    public Proto getProto() {
        return proto;
    }

    @Override
    public void setProto(Proto proto) {
        this.proto = proto;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean isNested() {
        return nested;
    }

    @Override
    public void setNested(boolean nested) {
        this.nested = nested;
    }

    @Override
    public UserTypeContainer getParent() {
        return parent;
    }

    @Override
    public void setParent(UserTypeContainer parent) {
        this.parent = parent;
    }

    @Override
    public String getReference() {
        return fullName;
    }
}
