package io.protostuff.compiler.model;

/**
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
