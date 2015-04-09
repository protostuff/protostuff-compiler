package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class AbstractUserType extends AbstractDescriptor implements UserType {

    protected Proto proto;
    protected String fullName;

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
}
