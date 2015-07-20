package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractUserType extends AbstractDescriptor implements UserType {

    protected Proto proto;
    protected String fullyQualifiedName;
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
    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    @Override
    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
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
