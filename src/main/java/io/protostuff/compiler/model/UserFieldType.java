package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface UserFieldType extends FieldType {

    /**
     * Short name. For example, {@code Baz}
     */
    String getName();

    void setName(String name);

    Proto getProto();

    void setProto(Proto proto);

    String getFullName();

    void setFullName(String fullName);

    // TODO: isNested can be removed: all messages that are not children of proto are nested
    boolean isNested();

    void setNested(boolean nested);

    UserTypeContainer getParent();

    void setParent(UserTypeContainer parent);

}
