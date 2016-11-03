package io.protostuff.generator.html.json.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.protostuff.compiler.model.DynamicMessage;
import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import io.protostuff.generator.html.json.index.NodeType;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableServiceDescriptor.class)
@JsonDeserialize(as = ImmutableServiceDescriptor.class)
public interface ServiceDescriptor {
    String name();

    NodeType type();

    String canonicalName();

    @Nullable
    String description();

    List<ServiceMethod> methods();

    Map<String, Object> options();
}
