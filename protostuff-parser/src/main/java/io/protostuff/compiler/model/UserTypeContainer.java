package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface UserTypeContainer extends Descriptor, MessageContainer, EnumContainer, ExtensionContainer {

    /**
     * Returns string prefix that is common for all children full names.
     * For root container it is a dot if package is not set.
     */
    String getNamespace();

}
