package io.protostuff.generator;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensibleStCompilerFactory {

    ProtoCompiler create(Collection<String> templates, ExtensionProvider extensionProvider);
}
