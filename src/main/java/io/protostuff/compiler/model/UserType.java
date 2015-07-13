package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface UserType extends Descriptor, FieldType {

    /**
     * Short name. For example, {@code Baz}
     */
    String getName();

    void setName(String name);

    Proto getProto();

    void setProto(Proto proto);

    String getFullName();

    void setFullName(String fullName);

    UserTypeContainer getParent();

}
