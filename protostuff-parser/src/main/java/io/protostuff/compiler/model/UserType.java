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

    /**
     * Returns fully qualified name for this user type. It always starts with dot.
     */
    String getFullyQualifiedName();

    void setFullyQualifiedName(String fullyQualifiedName);

    /**
     * Returns {@link #getFullyQualifiedName()} without leading dot.
     */
    String getCanonicalName();

    UserTypeContainer getParent();

}
