package io.protostuff.compiler.model;

import java.util.List;

/**
 * Container for extensions - a node where extensions can be defined.
 * Protocol buffer allow extensions to be defined in proto file or a message.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensionContainer {

    List<Extension> getDeclaredExtensions();

    void addDeclaredExtension(Extension extension);
}
