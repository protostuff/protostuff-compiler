package io.protostuff.compiler.model;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensionContainer {

    List<Extension> getExtensions();

    void addExtension(Extension extension);
}
