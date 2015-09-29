package io.protostuff.generator.java;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.FieldType;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.ScalarFieldType;
import io.protostuff.compiler.model.UserType;
import io.protostuff.generator.Formatter;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageFieldUtil {

    public static final String GETTER_PREFIX = "get";
    public static final String CHECK_SET_PREFIX = "has";

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
        return Formatter.toCamelCase(name);
    }

    public static String getFieldGetterName(Field field) {
        return GETTER_PREFIX + Formatter.toPascalCase(field.getName());
    }

    public static boolean isGenerateHasMethod(Field field) {
        return field.getType() instanceof Message;
    }

    public static String getHasMethodName(Field field) {
        return CHECK_SET_PREFIX + Formatter.toPascalCase(field.getName());
    }

    public static String getBuilderSetterName(Field field) {
        return Formatter.toPascalCase(field.getName());
    }
}
