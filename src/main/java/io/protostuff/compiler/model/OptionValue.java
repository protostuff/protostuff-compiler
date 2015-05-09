package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class OptionValue {

    private final String rawValue;

    public OptionValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getRawValue() {
        return rawValue;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("rawValue", rawValue)
                .toString();
    }
}
