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

    private FieldContainer parent;

    public Field(FieldContainer parent) {
        this.parent = parent;
    }

    @Override
    public FieldContainer getParent() {
        return parent;
    }

    public void setParent(Message parent) {
        this.parent = parent;
    }

    public FieldModifier getModifier() {
        return modifier == null ? FieldModifier.DEFAULT : modifier;
    }

    public void setModifier(FieldModifier modifier) {
        this.modifier = modifier;
    }

    public boolean hasModifier() {
        return modifier != null;
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

    /**
     * Test if this field is `repeated`.
     */
    public boolean isRepeated() {
        return getModifier() == FieldModifier.REPEATED;
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

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.MESSAGE_FIELD;
    }
}
