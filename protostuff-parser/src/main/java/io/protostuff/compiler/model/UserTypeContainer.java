package io.protostuff.compiler.model;

/**
 * Base interface for all nodes that can hold child user
 * types - it includes proto, message and group nodes.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface UserTypeContainer extends Descriptor, MessageContainer, EnumContainer, ExtensionContainer {

    /**
     * Returns string prefix that is common for all children full names.
     * For root container it is a dot if package is not set.
     */
    String getNamespace();

}
