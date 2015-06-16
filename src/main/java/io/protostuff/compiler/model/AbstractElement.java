package io.protostuff.compiler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class AbstractElement implements Element {
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
    public List<String> getComments() {
        if (comments == null) {
            return Collections.emptyList();
        }
        return comments;
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
