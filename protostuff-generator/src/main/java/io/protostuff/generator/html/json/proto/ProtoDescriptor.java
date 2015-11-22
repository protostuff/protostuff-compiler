package io.protostuff.generator.html.json.proto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

import javax.annotation.Nullable;

import io.protostuff.generator.html.json.index.NodeType;
import io.protostuff.generator.html.json.message.ImmutableMessageDescriptor;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableProtoDescriptor.class)
@JsonDeserialize(as = ImmutableProtoDescriptor.class)
public interface ProtoDescriptor {

    String name();

    NodeType type();

    String canonicalName();

    String filename();

    @Nullable
    String description();

}
