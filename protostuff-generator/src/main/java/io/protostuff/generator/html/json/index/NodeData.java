package io.protostuff.generator.html.json.index;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class NodeData {
    private final NodeType type;
    private final String ref;
    private final String file;

    private NodeData(Builder builder) {
        type = builder.type;
        ref = builder.ref;
        file = builder.file;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public NodeType getType() {
        return type;
    }

    public String getRef() {
        return ref;
    }

    public String getFile() {
        return file;
    }


    public static final class Builder {
        private NodeType type;
        private String ref;
        private String file;

        private Builder() {
        }

        public Builder type(NodeType val) {
            type = val;
            return this;
        }

        public Builder ref(String val) {
            ref = val;
            return this;
        }

        public Builder file(String val) {
            file = val;
            return this;
        }

        public NodeData build() {
            return new NodeData(this);
        }
    }
}
