package io.protostuff.compiler.model;

import java.util.List;

/**
 * Base interface for all proto elements.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface Descriptor extends Element {

    String getName();

    DynamicMessage getOptions();

}
