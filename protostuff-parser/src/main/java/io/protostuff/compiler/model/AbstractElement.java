package io.protostuff.compiler.model;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractElement implements Element {
    protected SourceCodeLocation sourceCodeLocation;
    protected List<String> comments;

    @Override
    public SourceCodeLocation getSourceCodeLocation() {
        if (sourceCodeLocation == null) {
            return SourceCodeLocation.UNKNOWN;
        }
        return sourceCodeLocation;
    }

    public void setSourceCodeLocation(SourceCodeLocation sourceCodeLocation) {
        this.sourceCodeLocation = sourceCodeLocation;
    }

    @Override
    public List<String> getCommentLines() {
        if (comments == null) {
            return Collections.emptyList();
        }
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
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(line);
    }
}
