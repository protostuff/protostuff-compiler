package io.protostuff.generator.java;

import com.google.common.collect.ImmutableMap;
import io.protostuff.compiler.model.ScalarFieldType;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ScalarFieldTypeUtil {

    private static final String JAVA_LANG_INTEGER = "Integer";
    private static final String JAVA_LANG_LONG = "Long";
    private static final String JAVA_LANG_FLOAT = "Float";
    private static final String JAVA_LANG_DOUBLE = "Double";
    private static final String JAVA_LANG_BOOLEAN = "Boolean";
    private static final String JAVA_LANG_STRING = "String";
    private static final String PROTOSTUFF_BYTE_STRING = "io.protostuff.ByteString";

    private static final String PRIMITIVE_INT = "int";
    private static final String PRIMITIVE_LONG = "long";
    private static final String PRIMITIVE_FLOAT = "float";
    private static final String PRIMITIVE_DOUBLE = "double";
    private static final String PRIMITIVE_BOOLEAN = "boolean";

    private static final String DEFAULT_VALUE_INT = "0";
    private static final String DEFAULT_VALUE_LONG = "0L";
    private static final String DEFAULT_VALUE_FLOAT = "0f";
    private static final String DEFAULT_VALUE_DOUBLE = "0d";
    private static final String DEFAULT_VALUE_BOOLEAN = "false";
    private static final String DEFAULT_VALUE_STRING = "\"\"";
    private static final String DEFAULT_VALUE_BYTE_STRING = "io.protostuff.ByteString.EMPTY";

    private static final Map<ScalarFieldType, String> WRAPPER_TYPE =
            new EnumMap<>(ImmutableMap.<ScalarFieldType, String>builder()
                    .put(ScalarFieldType.INT32, JAVA_LANG_INTEGER)
                    .put(ScalarFieldType.UINT32, JAVA_LANG_INTEGER)
                    .put(ScalarFieldType.SINT32, JAVA_LANG_INTEGER)
                    .put(ScalarFieldType.FIXED32, JAVA_LANG_INTEGER)
                    .put(ScalarFieldType.SFIXED32, JAVA_LANG_INTEGER)
                    .put(ScalarFieldType.INT64, JAVA_LANG_LONG)
                    .put(ScalarFieldType.UINT64, JAVA_LANG_LONG)
                    .put(ScalarFieldType.SINT64, JAVA_LANG_LONG)
                    .put(ScalarFieldType.FIXED64, JAVA_LANG_LONG)
                    .put(ScalarFieldType.SFIXED64, JAVA_LANG_LONG)
                    .put(ScalarFieldType.FLOAT, JAVA_LANG_FLOAT)
                    .put(ScalarFieldType.DOUBLE, JAVA_LANG_DOUBLE)
                    .put(ScalarFieldType.BOOL, JAVA_LANG_BOOLEAN)
                    .put(ScalarFieldType.STRING, JAVA_LANG_STRING)
                    .put(ScalarFieldType.BYTES, PROTOSTUFF_BYTE_STRING)
                    .build());
    private static final Map<ScalarFieldType, String> PRIMITIVE_TYPE =
            new EnumMap<>(ImmutableMap.<ScalarFieldType, String>builder()
                    .put(ScalarFieldType.INT32, PRIMITIVE_INT)
                    .put(ScalarFieldType.UINT32, PRIMITIVE_INT)
                    .put(ScalarFieldType.SINT32, PRIMITIVE_INT)
                    .put(ScalarFieldType.FIXED32, PRIMITIVE_INT)
                    .put(ScalarFieldType.SFIXED32, PRIMITIVE_INT)
                    .put(ScalarFieldType.INT64, PRIMITIVE_LONG)
                    .put(ScalarFieldType.UINT64, PRIMITIVE_LONG)
                    .put(ScalarFieldType.SINT64, PRIMITIVE_LONG)
                    .put(ScalarFieldType.FIXED64, PRIMITIVE_LONG)
                    .put(ScalarFieldType.SFIXED64, PRIMITIVE_LONG)
                    .put(ScalarFieldType.FLOAT, PRIMITIVE_FLOAT)
                    .put(ScalarFieldType.DOUBLE, PRIMITIVE_DOUBLE)
                    .put(ScalarFieldType.BOOL, PRIMITIVE_BOOLEAN)
                    .put(ScalarFieldType.STRING, JAVA_LANG_STRING)
                    .put(ScalarFieldType.BYTES, PROTOSTUFF_BYTE_STRING)
                    .build());
    private static final Map<ScalarFieldType, String> DEFAULT_VALUE =
            new EnumMap<>(ImmutableMap.<ScalarFieldType, String>builder()
                    .put(ScalarFieldType.INT32, DEFAULT_VALUE_INT)
                    .put(ScalarFieldType.UINT32, DEFAULT_VALUE_INT)
                    .put(ScalarFieldType.SINT32, DEFAULT_VALUE_INT)
                    .put(ScalarFieldType.FIXED32, DEFAULT_VALUE_INT)
                    .put(ScalarFieldType.SFIXED32, DEFAULT_VALUE_INT)
                    .put(ScalarFieldType.INT64, DEFAULT_VALUE_LONG)
                    .put(ScalarFieldType.UINT64, DEFAULT_VALUE_LONG)
                    .put(ScalarFieldType.SINT64, DEFAULT_VALUE_LONG)
                    .put(ScalarFieldType.FIXED64, DEFAULT_VALUE_LONG)
                    .put(ScalarFieldType.SFIXED64, DEFAULT_VALUE_LONG)
                    .put(ScalarFieldType.FLOAT, DEFAULT_VALUE_FLOAT)
                    .put(ScalarFieldType.DOUBLE, DEFAULT_VALUE_DOUBLE)
                    .put(ScalarFieldType.BOOL, DEFAULT_VALUE_BOOLEAN)
                    .put(ScalarFieldType.STRING, DEFAULT_VALUE_STRING)
                    .put(ScalarFieldType.BYTES, DEFAULT_VALUE_BYTE_STRING)
                    .build());

    private ScalarFieldTypeUtil() {
        throw new IllegalAccessError("Utility class");
    }

    @Nonnull
    public static String getWrapperType(ScalarFieldType type) {
        return getNonNull(WRAPPER_TYPE, type);
    }

    @Nonnull
    public static String getPrimitiveType(ScalarFieldType type) {
        return getNonNull(PRIMITIVE_TYPE, type);
    }

    @Nonnull
    public static String getDefaultValue(ScalarFieldType type) {
        return getNonNull(DEFAULT_VALUE, type);
    }

    @Nonnull
    private static String getNonNull(Map<ScalarFieldType, String> map, ScalarFieldType type) {
        String value = map.get(type);
        if (value == null) {
            throw new IllegalArgumentException(String.valueOf(type));
        }
        return value;
    }
}
