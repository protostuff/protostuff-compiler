package io.protostuff.generator.java;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.*;
import io.protostuff.generator.AbstractExtensionProvider;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JavaExtensionProvider extends AbstractExtensionProvider {

    public JavaExtensionProvider() {
        registerProperty(Proto.class, "javaPackage", ProtoUtil::getPackage);
        registerProperty(Proto.class, "javaPackagePath", ProtoUtil::getPackagePath);

        registerProperty(ScalarFieldType.class, "javaWrapperType", ScalarFieldTypeUtil::getWrapperType);
        registerProperty(ScalarFieldType.class, "javaPrimitiveType", ScalarFieldTypeUtil::getPrimitiveType);

        registerProperty(Message.class, "javaName", UserTypeUtil::getClassName);
        registerProperty(Message.class, "javaFullName", UserTypeUtil::getCanonicalName);
        registerProperty(Message.class, "hasFields", MessageUtil::hasFields);
        registerProperty(Message.class, "javaBitFieldNames", MessageUtil::bitFieldNames);

        registerProperty(Field.class, "javaType", MessageFieldUtil::getFieldType);
        registerProperty(Field.class, "javaRepeatedType", MessageFieldUtil::getRepeatedFieldType);
        registerProperty(Field.class, "javaIterableType", MessageFieldUtil::getIterableFieldType);
        registerProperty(Field.class, "javaWrapperType", MessageFieldUtil::getWrapperFieldType);
        registerProperty(Field.class, "javaName", MessageFieldUtil::getFieldName);
        registerProperty(Field.class, "jsonName", MessageFieldUtil::getJsonFieldName);
        registerProperty(Field.class, "javaGetterName", MessageFieldUtil::getFieldGetterName);
        registerProperty(Field.class, "javaSetterName", MessageFieldUtil::getFieldSetterName);
        registerProperty(Field.class, "javaCleanerName", MessageFieldUtil::getFieldCleanerName);
        registerProperty(Field.class, "javaEnumValueGetterName", MessageFieldUtil::getEnumFieldValueGetterName);
        registerProperty(Field.class, "javaEnumValueSetterName", MessageFieldUtil::getEnumFieldValueSetterName);

        registerProperty(Field.class, "javaRepeatedGetterName", MessageFieldUtil::getRepeatedFieldGetterName);
        registerProperty(Field.class, "javaRepeatedEnumConverterName", MessageFieldUtil::getRepeatedEnumConverterName);
        registerProperty(Field.class, "javaRepeatedSetterName", MessageFieldUtil::getRepeatedFieldSetterName);
        registerProperty(Field.class, "javaRepeatedAdderName", MessageFieldUtil::getRepeatedFieldAdderName);
        registerProperty(Field.class, "javaRepeatedAddAllName", MessageFieldUtil::getRepeatedFieldAddAllName);
        registerProperty(Field.class, "javaRepeatedEnumValueSetterName", MessageFieldUtil::getRepeatedEnumValueSetterName);
        registerProperty(Field.class, "javaRepeatedEnumValueAdderName", MessageFieldUtil::getRepeatedEnumValueAdderName);
        registerProperty(Field.class, "javaRepeatedEnumValueAddAllName", MessageFieldUtil::getRepeatedEnumValueAddAllName);
        registerProperty(Field.class, "javaRepeatedEnumValueGetterName", MessageFieldUtil::getRepeatedEnumFieldValueGetterName);
        registerProperty(Field.class, "javaRepeatedEnumValueGetterByIndexName", MessageFieldUtil::javaRepeatedEnumValueGetterByIndexName);

        registerProperty(Field.class, "javaRepeatedGetCountMethodName", MessageFieldUtil::repeatedGetCountMethodName);
        registerProperty(Field.class, "javaRepeatedGetByIndexMethodName", MessageFieldUtil::repeatedGetByIndexMethodName);

        registerProperty(Field.class, "javaMapGetterName", MessageFieldUtil::getMapGetterName);
        registerProperty(Field.class, "javaMapType", MessageFieldUtil::getMapFieldType);
        registerProperty(Field.class, "javaMapKeyType", MessageFieldUtil::getMapFieldKeyType);
        registerProperty(Field.class, "javaMapValueType", MessageFieldUtil::getMapFieldValueType);
        registerProperty(Field.class, "javaMapAdderName", MessageFieldUtil::getMapFieldAdderName);
        registerProperty(Field.class, "javaMapAddAllName", MessageFieldUtil::getMapFieldAddAllName);
        registerProperty(Field.class, "javaMapGetByKeyMethodName", MessageFieldUtil::mapGetByKeyMethodName);

        registerProperty(Field.class, "javaIsMessage", MessageFieldUtil::isMessage);
        registerProperty(Field.class, "javaHasMethodName", MessageFieldUtil::getHasMethodName);
        registerProperty(Field.class, "javaBuilderGetterName", MessageFieldUtil::getBuilderGetterName);
        registerProperty(Field.class, "javaBuilderSetterName", MessageFieldUtil::getBuilderSetterName);
        registerProperty(Field.class, "javaBuilderRepeatedSetterName", MessageFieldUtil::getRepeatedBuilderSetterName);
        registerProperty(Field.class, "javaDefaultValue", MessageFieldUtil::getDefaultValue);
        registerProperty(Field.class, "javaIsNumericType", MessageFieldUtil::isNumericType);
        registerProperty(Field.class, "javaIsBooleanType", MessageFieldUtil::isBooleanType);
        registerProperty(Field.class, "javaIsScalarNullableType", MessageFieldUtil::isScalarNullableType);
        registerProperty(Field.class, "protostuffReadMethod", MessageFieldUtil::protostuffReadMethod);
        registerProperty(Field.class, "protostuffWriteMethod", MessageFieldUtil::protostuffWriteMethod);
        registerProperty(Field.class, "toStringPart", MessageFieldUtil::toStringPart);
        registerProperty(Field.class, "javaBitFieldName", MessageFieldUtil::bitFieldName);
        registerProperty(Field.class, "javaBitFieldIndex", MessageFieldUtil::bitFieldIndex);
        registerProperty(Field.class, "javaBitFieldMask", MessageFieldUtil::bitFieldMask);
        registerProperty(Field.class, "javaOneofConstantName", MessageFieldUtil::javaOneofConstantName);

        registerProperty(Oneof.class, "javaName", MessageUtil::getOneofEnumClassName);
        registerProperty(Oneof.class, "javaNotSetConstantName", MessageUtil::getOneofNotSetConstantName);
        registerProperty(Oneof.class, "javaCaseGetterName", MessageUtil::getOneofCaseGetterName);
        registerProperty(Oneof.class, "javaCaseCleanerName", MessageUtil::getOneofCaseCleanerName);
        registerProperty(Oneof.class, "javaFieldName", MessageUtil::getOneofFieldName);
        registerProperty(Oneof.class, "javaCaseFieldName", MessageUtil::getOneofCaseFieldName);

        registerProperty(Enum.class, "javaName", UserTypeUtil::getClassName);
        registerProperty(Enum.class, "javaFullName", UserTypeUtil::getCanonicalName);

        registerProperty(EnumConstant.class, "javaName", EnumUtil::getName);

        registerProperty(Service.class, "javaName", ServiceUtil::getClassName);

        registerProperty(ServiceMethod.class, "javaName", ServiceUtil::getMethodName);
    }
}
