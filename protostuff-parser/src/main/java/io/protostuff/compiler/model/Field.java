package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import io.protostuff.compiler.model.DynamicMessage.Value;

/**
 * Field node.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class Field extends AbstractDescriptor {

    public static final int MAX_TAG_VALUE = (1 << 29) - 1;
    public static final Value DV_TRUE = Value.createBoolean(true);

    protected FieldModifier modifier;
    protected String typeName;
    protected FieldType type;
    protected int tag;
    protected int index;
    private FieldContainer parent;
    private Oneof oneof;

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
     * Field index in the container (order, starting from 1).
     */
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Oneof getOneof() {
        return oneof;
    }

    public void setOneof(Oneof oneof) {
        this.oneof = oneof;
    }

    public boolean isOneofPart() {
        return oneof != null;
    }

    /**
     * Test if this field is `repeated`.
     */
    public boolean isRepeated() {
        return getModifier() == FieldModifier.REPEATED;
    }

    public boolean isMap() {
        if (type instanceof Message) {
            Message message = (Message) type;
            return message.isMapEntry();
        }
        return false;
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
