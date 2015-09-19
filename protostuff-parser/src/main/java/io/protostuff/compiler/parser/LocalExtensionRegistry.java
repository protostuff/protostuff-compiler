package io.protostuff.compiler.parser;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import java.util.Collection;

import io.protostuff.compiler.model.Extension;

/**
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
