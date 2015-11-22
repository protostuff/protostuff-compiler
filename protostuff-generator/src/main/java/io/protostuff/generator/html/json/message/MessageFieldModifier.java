package io.protostuff.generator.html.json.message;

import com.google.common.base.Preconditions;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public enum MessageFieldModifier {

    @JsonProperty("optional")
    OPTIONAL,

    @JsonProperty("required")
    REQUIRED,

    @JsonProperty("repeated")
    REPEATED;

    public static MessageFieldModifier fromString(String s) {
        Preconditions.checkNotNull(s);
        switch (s.toLowerCase()) {
            case "optional":
                return OPTIONAL;
            case "required":
                return REQUIRED;
            case "repeated":
                return REPEATED;
            default:
                throw new IllegalArgumentException(s);
        }
    }
}
