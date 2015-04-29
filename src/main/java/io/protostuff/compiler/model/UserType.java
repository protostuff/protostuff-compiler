package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface UserType extends FieldType {

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

//    @Nullable
//    Message getParentMessage();

    /**
     * Fully qualified name of a message/enum. Includes package, all parent messages chain and
     * this message name. For example, {@code foo.bar.Message1.Baz}
     */
//    String getFullName();
}
