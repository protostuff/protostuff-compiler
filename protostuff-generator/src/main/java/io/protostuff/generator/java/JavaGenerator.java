package io.protostuff.generator.java;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.*;
import io.protostuff.generator.AbstractGenerator;
import io.protostuff.generator.StCompilerFactory;

import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JavaGenerator extends AbstractGenerator {

    public static final String GENERATOR_NAME = "java";

    @Inject
    public JavaGenerator(StCompilerFactory compilerFactory) {
        super(compilerFactory);

        extend(Proto.class, "generator", ProtoUtil::getGeneratorInfo);
        extend(Proto.class, "generator", ProtoUtil::getGeneratorInfo);
        extend(Proto.class, "javaPackage", ProtoUtil::getPackage);
        extend(Proto.class, "javaPackagePath", ProtoUtil::getPackagePath);

        extend(ScalarFieldType.class, "javaWrapperType", ScalarFieldTypeUtil::getWrapperType);
        extend(ScalarFieldType.class, "javaPrimitiveType", ScalarFieldTypeUtil::getPrimitiveType);

        extend(Message.class, "javaName", UserTypeUtil::getClassName);
        extend(Message.class, "javaFullName", UserTypeUtil::getCanonicalName);
        extend(Message.class, "hasFields", MessageUtil::hasFields);
        extend(Message.class, "javaBitFieldNames", MessageUtil::bitFieldNames);

        extend(Field.class, "javaType", MessageFieldUtil::getFieldType);
        extend(Field.class, "javaRepeatedType", MessageFieldUtil::getRepeatedFieldType);
        extend(Field.class, "javaIterableType", MessageFieldUtil::getIterableFieldType);
        extend(Field.class, "javaWrapperType", MessageFieldUtil::getWrapperFieldType);
        extend(Field.class, "javaName", MessageFieldUtil::getFieldName);
        extend(Field.class, "jsonName", MessageFieldUtil::getJsonFieldName);
        extend(Field.class, "javaGetterName", MessageFieldUtil::getFieldGetterName);
        extend(Field.class, "javaSetterName", MessageFieldUtil::getFieldSetterName);
        extend(Field.class, "javaCleanerName", MessageFieldUtil::getFieldCleanerName);

        extend(Field.class, "javaRepeatedGetterName", MessageFieldUtil::getRepeatedFieldGetterName);
        extend(Field.class, "javaRepeatedSetterName", MessageFieldUtil::getRepeatedFieldSetterName);
        extend(Field.class, "javaRepeatedAdderName", MessageFieldUtil::getRepeatedFieldAdderName);
        extend(Field.class, "javaRepeatedAddAllName", MessageFieldUtil::getRepeatedFieldAddAllName);
        extend(Field.class, "javaRepeatedGetCountMethodName", MessageFieldUtil::repeatedGetCountMethodName);
        extend(Field.class, "javaRepeatedGetByIndexMethodName", MessageFieldUtil::repeatedGetByIndexMethodName);

        extend(Field.class, "javaMapGetterName", MessageFieldUtil::getMapGetterName);
        extend(Field.class, "javaMapType", MessageFieldUtil::getMapFieldType);
        extend(Field.class, "javaMapKeyType", MessageFieldUtil::getMapFieldKeyType);
        extend(Field.class, "javaMapValueType", MessageFieldUtil::getMapFieldValueType);
        extend(Field.class, "javaMapAdderName", MessageFieldUtil::getMapFieldAdderName);
        extend(Field.class, "javaMapAddAllName", MessageFieldUtil::getMapFieldAddAllName);
        extend(Field.class, "javaMapGetByKeyMethodName", MessageFieldUtil::mapGetByKeyMethodName);

        extend(Field.class, "javaIsMessage", MessageFieldUtil::isMessage);
        extend(Field.class, "javaHasMethodName", MessageFieldUtil::getHasMethodName);
        extend(Field.class, "javaBuilderGetterName", MessageFieldUtil::getBuilderGetterName);
        extend(Field.class, "javaBuilderSetterName", MessageFieldUtil::getBuilderSetterName);
        extend(Field.class, "javaBuilderRepeatedSetterName", MessageFieldUtil::getRepeatedBuilderSetterName);
        extend(Field.class, "javaDefaultValue", MessageFieldUtil::getDefaultValue);
        extend(Field.class, "javaIsNumericType", MessageFieldUtil::isNumericType);
        extend(Field.class, "javaIsBooleanType", MessageFieldUtil::isBooleanType);
        extend(Field.class, "javaIsScalarNullableType", MessageFieldUtil::isScalarNullableType);
        extend(Field.class, "protostuffReadMethod", MessageFieldUtil::protostuffReadMethod);
        extend(Field.class, "protostuffWriteMethod", MessageFieldUtil::protostuffWriteMethod);
        extend(Field.class, "toStringPart", MessageFieldUtil::toStringPart);
        extend(Field.class, "javaBitFieldName", MessageFieldUtil::bitFieldName);
        extend(Field.class, "javaBitFieldIndex", MessageFieldUtil::bitFieldIndex);
        extend(Field.class, "javaBitFieldMask", MessageFieldUtil::bitFieldMask);
        extend(Field.class, "javaOneofConstantName", MessageFieldUtil::javaOneofConstantName);

        extend(Oneof.class, "javaName", MessageUtil::getOneofEnumClassName);
        extend(Oneof.class, "javaNotSetConstantName", MessageUtil::getOneofNotSetConstantName);
        extend(Oneof.class, "javaCaseGetterName", MessageUtil::getOneofCaseGetterName);
        extend(Oneof.class, "javaCaseCleanerName", MessageUtil::getOneofCaseCleanerName);
        extend(Oneof.class, "javaFieldName", MessageUtil::getOneofFieldName);
        extend(Oneof.class, "javaCaseFieldName", MessageUtil::getOneofCaseFieldName);

        extend(Enum.class, "javaName", UserTypeUtil::getClassName);
        extend(Enum.class, "javaFullName", UserTypeUtil::getCanonicalName);

        extend(EnumConstant.class, "javaName", EnumUtil::getName);

        extend(Service.class, "javaName", ServiceUtil::getClassName);

        extend(ServiceMethod.class, "javaName", ServiceUtil::getMethodName);
    }

    @Override
    public String getName() {
        return GENERATOR_NAME;
    }

}
