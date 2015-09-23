package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractUserType extends AbstractDescriptor implements UserType {

    protected final UserTypeContainer parent;
    protected Proto proto;
    protected String fullyQualifiedName;

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
    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    @Override
    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    @Override
    public boolean isScalar() {
        return false;
    }

    @Override
    public String getCanonicalName() {
        return getFullyQualifiedName().substring(1);
    }

    @Override
    public UserTypeContainer getParent() {
        return parent;
    }

}
