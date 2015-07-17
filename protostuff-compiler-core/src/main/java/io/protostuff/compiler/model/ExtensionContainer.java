package io.protostuff.compiler.model;

import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensionContainer {

    List<Extension> getDeclaredExtensions();

    void addDeclaredExtension(Extension extension);
}
