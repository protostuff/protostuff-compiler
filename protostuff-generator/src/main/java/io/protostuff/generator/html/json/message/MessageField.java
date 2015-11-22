package io.protostuff.generator.html.json.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

import javax.annotation.Nullable;

/**
 * @author Kostiantyn Shchepanovskyi
 */

@Value.Immutable
@JsonSerialize(as = ImmutableMessageField.class)
@JsonDeserialize(as = ImmutableMessageField.class)
public interface MessageField {
    String name();

    String typeId();

    MessageFieldModifier modifier();

    int tag();

    @Nullable
    String description();
}
