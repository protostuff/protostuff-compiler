package io.protostuff.generator;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface ExtensibleStCompilerFactory {

    ProtoCompiler create(String template, ExtensionProvider extensionProvider);
}
