package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FieldType {
    /**
     * Type reference. For scalar types, it returns one of:
     *
     * <ol>
     *     <li>{@code int32}</li>
     *     <li>{@code int64}</li>
     *     <li>{@code uint32}</li>
     *     <li>{@code uint64}</li>
     *     <li>{@code sint32}</li>
     *     <li>{@code sint64}</li>
     *     <li>{@code fixed32}</li>
     *     <li>{@code fixed64}</li>
     *     <li>{@code sfixed32}</li>
     *     <li>{@code sfixed64}</li>
     *     <li>{@code float}</li>
     *     <li>{@code double}</li>
     *     <li>{@code bool}</li>
     *     <li>{@code string}</li>
     *     <li>{@code bytes}</li>
     * </ol>
     *
     * For messages, it is full massage name.
     * For enums, it is full enum name.
     */
    String getReference();
}
