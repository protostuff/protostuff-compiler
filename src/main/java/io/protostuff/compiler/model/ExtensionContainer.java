package io.protostuff.compiler.model;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensionContainer {

    List<Extension> getExtensions();

    @Nullable
    default Extension getExtension(String name) {
        for (Extension extension : getExtensions()) {
            if (extension.name.equals(name)) {
                return extension;
            }
        }
        return null;
    }

    void addExtension(Extension extension);
}
