package io.protostuff.generator.html.json.index;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonTreeNode {
    private final String label;
    private NodeData data;
    private final List<JsonTreeNode> children;

    private JsonTreeNode(Builder builder) {
        label = builder.label;
        data = builder.data;
        children = builder.children;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getLabel() {
        return label;
    }

    public List<JsonTreeNode> getChildren() {
        return children;
    }

    public NodeData getData() {
        return data;
    }

    public static final class Builder {
        private String label;
        private NodeData data;
        private List<JsonTreeNode> children;

        private Builder() {
        }

        public Builder label(String val) {
            label = val;
            return this;
        }

        public Builder data(NodeData val) {
            data = val;
            return this;
        }

        public Builder children(List<JsonTreeNode> val) {
            children = val;
            return this;
        }

        public Builder addChild(JsonTreeNode val) {
            if (children == null) {
                children = new ArrayList<JsonTreeNode>();
            }
            children.add(val);
            return this;
        }

        public JsonTreeNode build() {
            return new JsonTreeNode(this);
        }
    }
}
