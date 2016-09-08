package io.protostuff.generator.java;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.*;
import io.protostuff.generator.AbstractExtensionProvider;
import io.protostuff.generator.ComputableProperty;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JavaExtensionProvider extends AbstractExtensionProvider {

    public JavaExtensionProvider() {
        registerProperty(Proto.class, "javaPackage", new ComputableProperty<Proto, Object>() {
            @Override
            public Object compute(Proto object) {
                return ProtoUtil.getPackage(object);
            }
        });
        registerProperty(Proto.class, "javaPackagePath", new ComputableProperty<Proto, Object>() {
            @Override
            public Object compute(Proto object) {
                return ProtoUtil.getPackagePath(object);
            }
        });

        registerProperty(ScalarFieldType.class, "javaWrapperType", new ComputableProperty<ScalarFieldType, Object>() {
            @Override
            public Object compute(ScalarFieldType object) {
                return ScalarFieldTypeUtil.getWrapperType(object);
            }
        });
        registerProperty(ScalarFieldType.class, "javaPrimitiveType", new ComputableProperty<ScalarFieldType, Object>() {
            @Override
            public Object compute(ScalarFieldType object) {
                return ScalarFieldTypeUtil.getPrimitiveType(object);
            }
        });

        registerProperty(Message.class, "javaName", new ComputableProperty<Message, Object>() {
            @Override
            public Object compute(Message object) {
                return UserTypeUtil.getClassName(object);
            }
        });
        registerProperty(Message.class, "javaFullName", new ComputableProperty<Message, Object>() {
            @Override
            public Object compute(Message object) {
                return UserTypeUtil.getCanonicalName(object);
            }
        });
        registerProperty(Message.class, "hasFields", new ComputableProperty<Message, Object>() {
            @Override
            public Object compute(Message object) {
                return MessageUtil.hasFields(object);
            }
        });
        registerProperty(Message.class, "javaBitFieldNames", new ComputableProperty<Message, Object>() {
            @Override
            public Object compute(Message object) {
                return MessageUtil.bitFieldNames(object);
            }
        });

        registerProperty(Field.class, "javaType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getFieldType(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedFieldType(object);
            }
        });
        registerProperty(Field.class, "javaIterableType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getIterableFieldType(object);
            }
        });
        registerProperty(Field.class, "javaWrapperType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getWrapperFieldType(object);
            }
        });
        registerProperty(Field.class, "javaName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getFieldName(object);
            }
        });
        registerProperty(Field.class, "jsonName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getJsonFieldName(object);
            }
        });
        registerProperty(Field.class, "javaGetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getFieldGetterName(object);
            }
        });
        registerProperty(Field.class, "javaSetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getFieldSetterName(object);
            }
        });
        registerProperty(Field.class, "javaCleanerName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getFieldCleanerName(object);
            }
        });
        registerProperty(Field.class, "javaEnumValueGetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getEnumFieldValueGetterName(object);
            }
        });
        registerProperty(Field.class, "javaEnumValueSetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getEnumFieldValueSetterName(object);
            }
        });

        registerProperty(Field.class, "javaRepeatedGetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedFieldGetterName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedEnumConverterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedEnumConverterName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedSetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedFieldSetterName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedAdderName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedFieldAdderName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedAddAllName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedFieldAddAllName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedEnumValueSetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedEnumValueSetterName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedEnumValueAdderName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedEnumValueAdderName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedEnumValueAddAllName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedEnumValueAddAllName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedEnumValueGetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedEnumFieldValueGetterName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedEnumValueGetterByIndexName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.javaRepeatedEnumValueGetterByIndexName(object);
            }
        });

        registerProperty(Field.class, "javaRepeatedGetCountMethodName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.repeatedGetCountMethodName(object);
            }
        });
        registerProperty(Field.class, "javaRepeatedGetByIndexMethodName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.repeatedGetByIndexMethodName(object);
            }
        });

        registerProperty(Field.class, "javaMapGetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getMapGetterName(object);
            }
        });
        registerProperty(Field.class, "javaMapType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getMapFieldType(object);
            }
        });
        registerProperty(Field.class, "javaMapKeyType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getMapFieldKeyType(object);
            }
        });
        registerProperty(Field.class, "javaMapValueType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getMapFieldValueType(object);
            }
        });
        registerProperty(Field.class, "javaMapAdderName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getMapFieldAdderName(object);
            }
        });
        registerProperty(Field.class, "javaMapAddAllName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getMapFieldAddAllName(object);
            }
        });
        registerProperty(Field.class, "javaMapGetByKeyMethodName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.mapGetByKeyMethodName(object);
            }
        });

        registerProperty(Field.class, "javaIsMessage", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.isMessage(object);
            }
        });
        registerProperty(Field.class, "javaHasMethodName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getHasMethodName(object);
            }
        });
        registerProperty(Field.class, "javaBuilderGetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getBuilderGetterName(object);
            }
        });
        registerProperty(Field.class, "javaBuilderSetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getBuilderSetterName(object);
            }
        });
        registerProperty(Field.class, "javaBuilderRepeatedSetterName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getRepeatedBuilderSetterName(object);
            }
        });
        registerProperty(Field.class, "javaDefaultValue", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.getDefaultValue(object);
            }
        });
        registerProperty(Field.class, "javaIsNumericType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.isNumericType(object);
            }
        });
        registerProperty(Field.class, "javaIsBooleanType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.isBooleanType(object);
            }
        });
        registerProperty(Field.class, "javaIsScalarNullableType", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.isScalarNullableType(object);
            }
        });
        registerProperty(Field.class, "protostuffReadMethod", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.protostuffReadMethod(object);
            }
        });
        registerProperty(Field.class, "protostuffWriteMethod", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.protostuffWriteMethod(object);
            }
        });
        registerProperty(Field.class, "toStringPart", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.toStringPart(object);
            }
        });
        registerProperty(Field.class, "javaBitFieldName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.bitFieldName(object);
            }
        });
        registerProperty(Field.class, "javaBitFieldIndex", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.bitFieldIndex(object);
            }
        });
        registerProperty(Field.class, "javaBitFieldMask", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.bitFieldMask(object);
            }
        });
        registerProperty(Field.class, "javaOneofConstantName", new ComputableProperty<Field, Object>() {
            @Override
            public Object compute(Field object) {
                return MessageFieldUtil.javaOneofConstantName(object);
            }
        });

        registerProperty(Oneof.class, "javaName", new ComputableProperty<Oneof, Object>() {
            @Override
            public Object compute(Oneof object) {
                return MessageUtil.getOneofEnumClassName(object);
            }
        });
        registerProperty(Oneof.class, "javaNotSetConstantName", new ComputableProperty<Oneof, Object>() {
            @Override
            public Object compute(Oneof object) {
                return MessageUtil.getOneofNotSetConstantName(object);
            }
        });
        registerProperty(Oneof.class, "javaCaseGetterName", new ComputableProperty<Oneof, Object>() {
            @Override
            public Object compute(Oneof object) {
                return MessageUtil.getOneofCaseGetterName(object);
            }
        });
        registerProperty(Oneof.class, "javaCaseCleanerName", new ComputableProperty<Oneof, Object>() {
            @Override
            public Object compute(Oneof object) {
                return MessageUtil.getOneofCaseCleanerName(object);
            }
        });
        registerProperty(Oneof.class, "javaFieldName", new ComputableProperty<Oneof, Object>() {
            @Override
            public Object compute(Oneof object) {
                return MessageUtil.getOneofFieldName(object);
            }
        });
        registerProperty(Oneof.class, "javaCaseFieldName", new ComputableProperty<Oneof, Object>() {
            @Override
            public Object compute(Oneof object) {
                return MessageUtil.getOneofCaseFieldName(object);
            }
        });

        registerProperty(Enum.class, "javaName", new ComputableProperty<Enum, Object>() {
            @Override
            public Object compute(Enum object) {
                return UserTypeUtil.getClassName(object);
            }
        });
        registerProperty(Enum.class, "javaFullName", new ComputableProperty<Enum, Object>() {
            @Override
            public Object compute(Enum object) {
                return UserTypeUtil.getCanonicalName(object);
            }
        });

        registerProperty(EnumConstant.class, "javaName", new ComputableProperty<EnumConstant, Object>() {
            @Override
            public Object compute(EnumConstant object) {
                return EnumUtil.getName(object);
            }
        });

        registerProperty(Service.class, "javaName", new ComputableProperty<Service, Object>() {
            @Override
            public Object compute(Service object) {
                return ServiceUtil.getClassName(object);
            }
        });

        registerProperty(ServiceMethod.class, "javaName", new ComputableProperty<ServiceMethod, Object>() {
            @Override
            public Object compute(ServiceMethod object) {
                return ServiceUtil.getMethodName(object);
            }
        });
    }
}
