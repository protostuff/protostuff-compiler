package io.protostuff.compiler.model;

import java.util.List;

/**
 * Base interface for all proto file nodes.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface Element {

    SourceCodeLocation getSourceCodeLocation();

    List<String> getCommentLines();

    String getComments();

    Element getParent();
}
