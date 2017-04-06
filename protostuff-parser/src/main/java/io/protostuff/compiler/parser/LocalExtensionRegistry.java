package io.protostuff.compiler.parser;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import io.protostuff.compiler.model.Extension;
import java.util.Collection;

/**
 * Extension registry for a single proto file.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class LocalExtensionRegistry extends AbstractExtensionRegistry {

    private final SetMultimap<String, Extension> extensions = HashMultimap.create();


    @Override
    public void registerExtension(Extension extension) {
        String extendeeFullyQualifiedName = extension.getExtendee().getFullyQualifiedName();
        Preconditions.checkNotNull(extendeeFullyQualifiedName);
        extensions.put(extendeeFullyQualifiedName, extension);
    }

    @Override
    public Collection<Extension> getExtensions(String messageName) {
        return extensions.get(messageName);
    }

}
