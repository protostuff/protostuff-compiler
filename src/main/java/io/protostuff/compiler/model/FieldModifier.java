package io.protostuff.compiler.model;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public enum FieldModifier {

    OPTIONAL("optional"),
    REQUIRED("required"),
    REPEATED("repeated");

    public static final FieldModifier DEFAULT = OPTIONAL;

    private final String value;

    FieldModifier(String value) {
        this.value = value;
    }

    public static FieldModifier parse(String s) {
        checkNotNull(s, "Can not parse field modifier from 'null'");
        for (FieldModifier m : values()) {
            if (m.value.equals(s)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Unknown field modifier: " + s);
    }

    @Override
    public String toString() {
        return value;
    }
}
