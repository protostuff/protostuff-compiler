package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface UserTypeContainer extends MessageContainer, EnumContainer, ExtensionContainer {

    /**
     * Returns string prefix that is common for all children full names.
     * For root container it is an empty string if package is not set.
     *
     * @return
     */
    String getNamespacePrefix();

}
