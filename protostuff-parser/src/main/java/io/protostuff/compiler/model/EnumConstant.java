package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * Enum value (constant).
 *
 * @author Kostiantyn Shchepanovskyi
 * @see Enum
 */
public class EnumConstant extends AbstractDescriptor {

    protected int value;

    private Enum parent;

    public EnumConstant(Enum parent) {
        this.parent = parent;
    }

    @Override
    public Enum getParent() {
        return parent;
    }

    public void setParent(Enum parent) {
        this.parent = parent;
    }

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.ENUM_CONSTANT;
    }

    /**
     * Returns numeric value of this enum constant that is used in serialized form.
     */
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("name", name)
                .add("value", value)
                .add("options", options)
                .toString();
    }

}
