package io.protostuff.compiler.model;

/**
 * Base interface for protocol buffers types that can be used as field type.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface FieldType extends Type {

    @Override
    String getName();

    /**
     * Type reference. For scalar types, it returns one of:
     * <p>
     * <ol>
     * <li>{@code int32}</li>
     * <li>{@code int64}</li>
     * <li>{@code uint32}</li>
     * <li>{@code uint64}</li>
     * <li>{@code sint32}</li>
     * <li>{@code sint64}</li>
     * <li>{@code fixed32}</li>
     * <li>{@code fixed64}</li>
     * <li>{@code sfixed32}</li>
     * <li>{@code sfixed64}</li>
     * <li>{@code float}</li>
     * <li>{@code double}</li>
     * <li>{@code bool}</li>
     * <li>{@code string}</li>
     * <li>{@code bytes}</li>
     * </ol>
     * <p>
     * For messages, it is full massage name.
     * For enums, it is full enum name.
     */
    @Override
    String getFullyQualifiedName();

    /**
     * Returns {@link #getFullyQualifiedName()} without leading dot.
     */
    @Override
    default String getCanonicalName() {
        String fqn = getFullyQualifiedName();
        if (fqn.startsWith(".")) {
            return  fqn.substring(1);
        }
        return fqn;
    }

    /**
     * Test if this type is scalar.
     */
    boolean isScalar();

    /**
     * Test if this type is enum.
     */
    boolean isEnum();

    /**
     * Test if this type is message.
     */
    boolean isMessage();

    /**
     * Test if this type is map.
     */
    boolean isMap();


}
