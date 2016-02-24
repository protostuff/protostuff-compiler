package io.protostuff.generator.java;

import io.protostuff.compiler.model.ScalarFieldType;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ScalarFieldTypeUtil {

    public static String getWrapperType(ScalarFieldType type) {
        String wrapperType;
        switch (type) {
            case INT32:
            case UINT32:
            case SINT32:
            case FIXED32:
            case SFIXED32:
                wrapperType = "Integer";
                break;
            case INT64:
            case UINT64:
            case SINT64:
            case FIXED64:
            case SFIXED64:
                wrapperType = "Long";
                break;
            case FLOAT:
                wrapperType = "Float";
                break;
            case DOUBLE:
                wrapperType = "Double";
                break;
            case BOOL:
                wrapperType = "Boolean";
                break;
            case STRING:
                wrapperType = "String";
                break;
            case BYTES:
                wrapperType = "io.protostuff.ByteString";
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(type));
        }
        return wrapperType;
    }

    public static String getPrimitiveType(ScalarFieldType type) {
        String primitiveType;
        switch (type) {
            case INT32:
            case UINT32:
            case SINT32:
            case FIXED32:
            case SFIXED32:
                primitiveType = "int";
                break;
            case INT64:
            case UINT64:
            case SINT64:
            case FIXED64:
            case SFIXED64:
                primitiveType = "long";
                break;
            case FLOAT:
                primitiveType = "float";
                break;
            case DOUBLE:
                primitiveType = "double";
                break;
            case BOOL:
                primitiveType  ="boolean";
                break;
            case STRING:
                primitiveType = "String";
                break;
            case BYTES:
                primitiveType = "io.protostuff.ByteString";
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(type));
        }
        return primitiveType;
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
                return "io.protostuff.ByteString.EMPTY";
            default:
                throw new IllegalArgumentException(String.valueOf(type));
        }
    }
}
