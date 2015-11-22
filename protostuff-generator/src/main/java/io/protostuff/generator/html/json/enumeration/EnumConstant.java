package io.protostuff.generator.html.json.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

import java.util.Optional;

import javax.annotation.Nullable;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableEnumConstant.class)
@JsonDeserialize(as = ImmutableEnumConstant.class)
public interface EnumConstant {

    String name();

    @Nullable
    String description();

    int value();
}
