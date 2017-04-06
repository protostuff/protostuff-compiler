package io.protostuff.compiler.model;

/**
 * Field modifier.
 * "optional" and "required" field modifiers are forbidden in proto3
 * (all fields are "optional" if no modifier specified).
 *
 * @author Kostiantyn Shchepanovskyi
 */
public enum FieldModifier {

    OPTIONAL,
    REQUIRED,
    REPEATED;

    public static final FieldModifier DEFAULT = OPTIONAL;

    private final String text;

    FieldModifier() {
        text = super.toString().toLowerCase();
    }

    @Override
    public String toString() {
        return text;
    }
}
