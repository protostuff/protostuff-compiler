package io.protostuff.compiler.model;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for all proto nodes.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractElement implements Element {
    protected SourceCodeLocation sourceCodeLocation = SourceCodeLocation.UNKNOWN;
    protected List<String> comments = new ArrayList<>();

    @Override
    public SourceCodeLocation getSourceCodeLocation() {
        return sourceCodeLocation;
    }

    public void setSourceCodeLocation(SourceCodeLocation sourceCodeLocation) {
        this.sourceCodeLocation = sourceCodeLocation;
    }

    @Override
    public List<String> getCommentLines() {
        return comments;
    }

    @Override
    public String getComments() {
        return Joiner.on('\n').join(getCommentLines());
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void addComment(String line) {
        comments.add(line);
    }
}
