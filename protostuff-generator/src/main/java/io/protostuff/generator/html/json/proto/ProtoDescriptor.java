package io.protostuff.generator.html.json.proto;

import io.protostuff.generator.html.json.index.NodeType;

import javax.annotation.Nullable;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoDescriptor {

    private String name;
    private NodeType type;
    private String canonicalName;
    private String filename;
    @Nullable
    private String description;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
