package io.protostuff.generator;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.Map;

public interface ExtensionProvider {

    Map<Class<?>, AttributeRenderer> attributeRenderers();

    Map<Class<?>, PropertyProvider<?>> propertyProviders();
}
