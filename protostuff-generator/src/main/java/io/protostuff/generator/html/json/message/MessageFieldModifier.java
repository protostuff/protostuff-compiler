package io.protostuff.generator.html.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

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

        String mod = s.toLowerCase();
        if ("optional".equals(mod)) {
            return OPTIONAL;
        }
        if ("required".equals(mod)) {
            return REQUIRED;
        }
        if ("repeated".equals(mod)) {
            return REPEATED;
        }
        throw new IllegalArgumentException(s);
    }
}
