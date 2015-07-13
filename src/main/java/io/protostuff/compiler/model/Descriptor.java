package io.protostuff.compiler.model;

/**
 * Base interface for all proto elements.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface Descriptor extends Element {

    String getName();

    DynamicMessage getOptions();

    DescriptorType getDescriptorType();

    Element getParent();
}
