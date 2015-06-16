package io.protostuff.compiler.model;

import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface Element {

    SourceCodeLocation getSourceCodeLocation();

    List<String> getComments();
}
