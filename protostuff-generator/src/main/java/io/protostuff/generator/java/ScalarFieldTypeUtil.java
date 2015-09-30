package io.protostuff.generator.java;

import java.nio.ByteBuffer;

import io.protostuff.compiler.model.ScalarFieldType;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ScalarFieldTypeUtil {

    public static String getWrapperType(ScalarFieldType type) {
        Class<?> wrapperType;
        switch (type) {
            case INT32:
            case UINT32:
            case SINT32:
            case FIXED32:
            case SFIXED32:
                wrapperType = Integer.class;
                break;
            case INT64:
            case UINT64:
            case SINT64:
            case FIXED64:
            case SFIXED64:
                wrapperType = Long.class;
                break;
            case FLOAT:
                wrapperType = Float.class;
                break;
            case DOUBLE:
                wrapperType = Double.class;
                break;
            case BOOL:
                wrapperType = Boolean.class;
                break;
            case STRING:
                wrapperType = String.class;
                break;
            case BYTES:
                wrapperType = ByteBuffer.class;
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(type));
        }
        return wrapperType.getSimpleName();
    }

    public static String getPrimitiveType(ScalarFieldType type) {
        Class<?> primitiveType;
        switch (type) {
            case INT32:
            case UINT32:
            case SINT32:
            case FIXED32:
            case SFIXED32:
                primitiveType = int.class;
                break;
            case INT64:
            case UINT64:
            case SINT64:
            case FIXED64:
            case SFIXED64:
                primitiveType = long.class;
                break;
            case FLOAT:
                primitiveType = float.class;
                break;
            case DOUBLE:
                primitiveType = double.class;
                break;
            case BOOL:
                primitiveType = boolean.class;
                break;
            case STRING:
                primitiveType = String.class;
                break;
            case BYTES:
                primitiveType = byte[].class;
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(type));
        }
        return primitiveType.getSimpleName();
    }

    public static String getDefaultValue(ScalarFieldType type) {
        switch (type) {
            case INT32:
            case UINT32:
            case SINT32:
            case FIXED32:
            case SFIXED32:
                return "0";
            case INT64:
            case UINT64:
            case SINT64:
            case FIXED64:
            case SFIXED64:
                return "0L";
            case FLOAT:
                return "0f";
            case DOUBLE:
                return "0d";
            case BOOL:
                return "false";
            case STRING:
                return "\"\"";
            case BYTES:
                // TODO optimize
                return "new byte[0]";
            default:
                throw new IllegalArgumentException(String.valueOf(type));
        }
    }
}
