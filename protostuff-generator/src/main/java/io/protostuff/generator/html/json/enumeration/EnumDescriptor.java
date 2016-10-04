package io.protostuff.generator.html.json.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

import java.util.List;

import javax.annotation.Nullable;

import io.protostuff.generator.html.json.index.NodeType;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableEnumDescriptor.class)
@JsonDeserialize(as = ImmutableEnumDescriptor.class)
public interface EnumDescriptor {

    String name();

    NodeType type();

    String canonicalName();

    @Nullable
    String description();

    List<EnumConstant> constants();
}
