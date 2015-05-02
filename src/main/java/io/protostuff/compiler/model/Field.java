package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Field extends AbstractDescriptor {

    public static final int MAX_TAG_VALUE = (1 << 29) - 1;

    protected FieldModifier modifier;
    protected String typeName;
    protected FieldType type;
    protected int tag;

    public FieldModifier getModifier() {
        return modifier == null ? FieldModifier.DEFAULT : modifier;
    }

    public boolean hasModifier() {
        return modifier != null;
    }

    public void setModifier(FieldModifier modifier) {
        this.modifier = modifier;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("name", name)
                .add("modifier", modifier)
                .add("typeName", typeName)
                .add("tag", tag)
                .add("options", options)
                .toString();
    }

}
