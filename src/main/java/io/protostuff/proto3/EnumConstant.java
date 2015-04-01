package io.protostuff.proto3;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enum value (constant).
 *
 * @author Kostiantyn Shchepanovskyi
 * @see io.protostuff.proto3.Enum
 */
public class EnumConstant extends AbstractDescriptor {

    protected int value;

    /**
     * Returns name of this enum constant (human-readable string).
     */
    public String getName() {
        return super.getName();
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
                .add("standardOptions", standardOptions)
                .add("customOptions", customOptions)
                .toString();
    }

}
