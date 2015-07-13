package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractUserType extends AbstractDescriptor implements UserType {

    protected Proto proto;
    protected String fullName;
    protected final UserTypeContainer parent;

    public AbstractUserType(UserTypeContainer parent) {
        this.parent = parent;
    }

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
    public UserTypeContainer getParent() {
        return parent;
    }

    @Override
    public String getReference() {
        return fullName;
    }
}
