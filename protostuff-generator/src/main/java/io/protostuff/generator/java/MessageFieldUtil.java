package io.protostuff.generator.java;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.FieldType;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.ScalarFieldType;
import io.protostuff.compiler.model.UserType;
import io.protostuff.generator.Formatter;

import static io.protostuff.compiler.model.ScalarFieldType.BYTES;
import static io.protostuff.compiler.model.ScalarFieldType.STRING;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageFieldUtil {

    public static final String HAS_PREFIX = "has";
    public static final String GETTER_PREFIX = "get";
    public static final String SETTER_PREFIX = "set";
    public static final String GET_DEFAULT_INSTANCE = "getDefaultInstance()";

    public static String getFieldType(Field field) {
        FieldType type = field.getType();
        if (type instanceof ScalarFieldType) {
            ScalarFieldType scalarFieldType = (ScalarFieldType) type;
            return ScalarFieldTypeUtil.getPrimitiveType(scalarFieldType);
        }
        if (type instanceof UserType) {
            UserType userType = (UserType) type;
            return UserTypeUtil.getCanonicalName(userType);
        }
        throw new IllegalArgumentException(String.valueOf(field.getType()));
    }

    public static String getFieldName(Field field) {
        String name = field.getName();
        String formattedName = Formatter.toCamelCase(name);
        if (isReservedKeyword(formattedName)) {
            return formattedName + '_';
        }
        return formattedName;
    }

    private static boolean isReservedKeyword(String formattedName) {
        return JavaConstants.RESERVED_KEYWORDS.contains(formattedName);
    }

    public static String getFieldGetterName(Field field) {
        return GETTER_PREFIX + Formatter.toPascalCase(field.getName());
    }

    public static boolean isGenerateHasMethod(Field field) {
        return field.getType() instanceof Message;
    }

    public static String getHasMethodName(Field field) {
        return HAS_PREFIX + Formatter.toPascalCase(field.getName());
    }

    public static String getBuilderSetterName(Field field) {
        return SETTER_PREFIX + Formatter.toPascalCase(field.getName());
    }

    public static String getDefaultValue(Field field) {
        FieldType type = field.getType();
        if (type instanceof ScalarFieldType) {
            return ScalarFieldTypeUtil.getDefaultValue((ScalarFieldType) type);
        }
        if (type instanceof UserType) {
            UserType userType = (UserType) type;
            return UserTypeUtil.getCanonicalName(userType) + '.' + GET_DEFAULT_INSTANCE;
        }
        throw new IllegalArgumentException(String.valueOf(type));
    }

    /**
     * Check if field type used to store value in java is nullable type.
     */
    public static boolean isNullableType(Field field) {
        FieldType type = field.getType();
        return STRING.equals(type) || BYTES.equals(type) || !(type instanceof ScalarFieldType);
    }
}
