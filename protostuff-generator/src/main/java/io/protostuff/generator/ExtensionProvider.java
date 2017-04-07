package io.protostuff.generator;

import java.util.Map;
import org.stringtemplate.v4.AttributeRenderer;

/**
 * Extension provider for StringTemplate engine.
 */
public interface ExtensionProvider {

    Map<Class<?>, AttributeRenderer> attributeRenderers();

    Map<Class<?>, PropertyProvider> propertyProviders();
}
