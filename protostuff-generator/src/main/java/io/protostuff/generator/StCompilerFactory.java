package io.protostuff.generator;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface StCompilerFactory {

    ProtoCompiler create(String template, Map<Class<?>, AttributeRenderer> rendererMap);
}
