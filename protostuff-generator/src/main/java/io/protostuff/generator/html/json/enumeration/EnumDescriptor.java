package io.protostuff.generator.html.json.enumeration;

import io.protostuff.generator.html.json.index.NodeType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumDescriptor {

    private String name;
    private NodeType type;
    private String canonicalName;
    @Nullable
    private String description;
    private List<EnumConstant> constants = new ArrayList<EnumConstant>();

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

    public List<EnumConstant> getConstants() {
        return constants;
    }

    public void setConstants(List<EnumConstant> constants) {
        this.constants = constants;
    }
}
