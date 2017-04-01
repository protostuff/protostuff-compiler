package io.protostuff.generator.html.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

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

    private static final ImmutableMap<String, MessageFieldModifier> MODIFIER_BY_PROTO_NAME =
            ImmutableMap.<String, MessageFieldModifier>builder()
                    .put("optional", OPTIONAL)
                    .put("required", REQUIRED)
                    .put("repeated", REPEATED)
                    .build();

    @Nonnull
    public static MessageFieldModifier fromString(String s) {
        checkNotNull(s);
        MessageFieldModifier modifier = MODIFIER_BY_PROTO_NAME.get(s);
        checkNotNull(modifier, "Could not find modifier for '%s'", s);
        return modifier;
    }
}
