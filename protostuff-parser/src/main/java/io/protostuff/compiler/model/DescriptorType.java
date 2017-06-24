package io.protostuff.compiler.model;

/**
 * Descriptor type for options lookup.
 * For each descriptor type there is a pre-defined message
 * in the {@code google/protobuf/descriptor.proto}.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public enum DescriptorType {

    PROTO,
    ENUM,
    ENUM_CONSTANT,
    MESSAGE,
    MESSAGE_FIELD,
    MAP,
    GROUP,
    SERVICE,
    SERVICE_METHOD,
    ONEOF
}
