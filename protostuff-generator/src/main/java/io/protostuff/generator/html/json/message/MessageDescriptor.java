package io.protostuff.generator.html.json.message;

import io.protostuff.generator.html.json.index.NodeType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageDescriptor {

    private String name;
    private NodeType type;
    private String canonicalName;
    @Nullable
    private String description;
    private List<MessageField> fields = new ArrayList<MessageField>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public List<MessageField> getFields() {
        return fields;
    }

    public void setFields(List<MessageField> fields) {
        this.fields = fields;
    }
}
