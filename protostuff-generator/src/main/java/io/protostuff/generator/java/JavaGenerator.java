package io.protostuff.generator.java;

import com.google.common.collect.ImmutableMap;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.*;
import io.protostuff.generator.ObjectExtender;
import io.protostuff.generator.ProtoCompiler;
import io.protostuff.generator.SimpleObjectExtender;
import io.protostuff.generator.StCompilerFactory;
import org.stringtemplate.v4.AttributeRenderer;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JavaGenerator implements ProtoCompiler {
    public static final String GENERATOR_NAME = "java";

    public static final String SERVICE_RETURN_TYPE_OPTION = "rpcAsyncReturnType";
    private final ProtoCompiler messageGenerator;
    private final ProtoCompiler enumGenerator;
    private final ProtoCompiler serviceGenerator;

    @Inject
    public JavaGenerator(StCompilerFactory compilerFactory) {
        // TODO initialization should be lazy - usually only one generator is used
        Map<Class<?>, AttributeRenderer> rendererMap = new HashMap<>();
        Map<Class<?>, ObjectExtender<?>> extenderMap = ImmutableMap.<Class<?>, ObjectExtender<?>>builder()
                .put(Proto.class, SimpleObjectExtender.<Proto>newBuilder()
                        .property("generator", ProtoUtil::getGeneratorInfo)
                        .property("javaPackage", ProtoUtil::getPackage)
                        .property("javaPackagePath", ProtoUtil::getPackagePath)
                        .build())
                .put(ScalarFieldType.class, SimpleObjectExtender.<ScalarFieldType>newBuilder()
                        .property("javaWrapperType", ScalarFieldTypeUtil::getWrapperType)
                        .property("javaPrimitiveType", ScalarFieldTypeUtil::getPrimitiveType)
                        .build())
                .put(Message.class, SimpleObjectExtender.<Message>newBuilder()
                        .property("javaName", UserTypeUtil::getClassName)
                        .property("javaFullName", UserTypeUtil::getCanonicalName)
                        .property("hasFields", MessageUtil::hasFields)
                        .property("javaBitFieldNames", MessageUtil::bitFieldNames)
                        .build())
                .put(Field.class, SimpleObjectExtender.<Field>newBuilder()
                        .property("javaType", MessageFieldUtil::getFieldType)
                        .property("javaRepeatedType", MessageFieldUtil::getRepeatedFieldType)
                        .property("javaName", MessageFieldUtil::getFieldName)
                        .property("jsonName", MessageFieldUtil::getJsonFieldName)
                        .property("javaGetterName", MessageFieldUtil::getFieldGetterName)
                        .property("javaSetterName", MessageFieldUtil::getFieldSetterName)
                        .property("javaCleanerName", MessageFieldUtil::getFieldCleanerName)

                        .property("javaRepeatedGetterName", MessageFieldUtil::getRepeatedFieldGetterName)
                        .property("javaRepeatedAdderName", MessageFieldUtil::getRepeatedFieldAdderName)
                        .property("javaRepeatedAddAllName", MessageFieldUtil::getRepeatedFieldAddAllName)
                        .property("javaRepeatedGetCountMethodName", MessageFieldUtil::repeatedGetCountMethodName)
                        .property("javaRepeatedGetByIndexMethodName", MessageFieldUtil::repeatedGetByIndexMethodName)

                        .property("javaMapGetterName", MessageFieldUtil::getMapGetterName)
                        .property("javaMapType", MessageFieldUtil::getMapFieldType)
                        .property("javaMapKeyType", MessageFieldUtil::getMapFieldKeyType)
                        .property("javaMapValueType", MessageFieldUtil::getMapFieldValueType)
                        .property("javaMapAdderName", MessageFieldUtil::getMapFieldAdderName)
                        .property("javaMapAddAllName", MessageFieldUtil::getMapFieldAddAllName)
                        .property("javaMapGetByKeyMethodName", MessageFieldUtil::mapGetByKeyMethodName)

                        .property("javaIsMessage", MessageFieldUtil::isMessage)
                        .property("javaHasMethodName", MessageFieldUtil::getHasMethodName)
                        .property("javaBuilderGetterName", MessageFieldUtil::getBuilderGetterName)
                        .property("javaBuilderSetterName", MessageFieldUtil::getBuilderSetterName)
                        .property("javaBuilderRepeatedSetterName", MessageFieldUtil::getRepeatedBuilderSetterName)
                        .property("javaDefaultValue", MessageFieldUtil::getDefaultValue)
                        .property("javaIsNumericType", MessageFieldUtil::isNumericType)
                        .property("javaIsBooleanType", MessageFieldUtil::isBooleanType)
                        .property("javaIsScalarNullableType", MessageFieldUtil::isScalarNullableType)
                        .property("protostuffReadMethod", MessageFieldUtil::protostuffReadMethod)
                        .property("protostuffWriteMethod", MessageFieldUtil::protostuffWriteMethod)
                        .property("toStringPart", MessageFieldUtil::toStringPart)
                        .property("javaBitFieldName", MessageFieldUtil::bitFieldName)
                        .property("javaBitFieldIndex", MessageFieldUtil::bitFieldIndex)
                        .property("javaBitFieldMask", MessageFieldUtil::bitFieldMask)
                        .property("javaOneofConstantName", MessageFieldUtil::javaOneofConstantName)
                        .build())
                .put(Oneof.class, SimpleObjectExtender.<Oneof>newBuilder()
                        .property("javaName", MessageUtil::getOneofEnumClassName)
                        .property("javaNotSetConstantName", MessageUtil::getOneofNotSetConstantName)
                        .property("javaCaseGetterName", MessageUtil::getOneofCaseGetterName)
                        .property("javaCaseCleanerName", MessageUtil::getOneofCaseCleanerName)
                        .property("javaFieldName", MessageUtil::getOneofFieldName)
                        .property("javaCaseFieldName", MessageUtil::getOneofCaseFieldName)
                        .build())
                .put(Enum.class, SimpleObjectExtender.<Enum>newBuilder()
                        .property("javaName", UserTypeUtil::getClassName)
                        .property("javaFullName", UserTypeUtil::getCanonicalName)
                        .build())
                .put(EnumConstant.class, SimpleObjectExtender.<EnumConstant>newBuilder()
                        .property("javaName", EnumUtil::getName)
                        .build())
                .put(Service.class, SimpleObjectExtender.<Service>newBuilder()
                        .property("javaName", ServiceUtil::getClassName)
                        .build())
                .put(ServiceMethod.class, SimpleObjectExtender.<ServiceMethod>newBuilder()
                        .property("javaName", ServiceUtil::getMethodName)
                        .property("asyncReturnType", ServiceUtil::getAsyncReturnType)
                        .build())
                .build();

        int n = 1;
        messageGenerator = compilerFactory.create("io/protostuff/generator/java/message.stg", rendererMap, extenderMap);
        enumGenerator = compilerFactory.create("io/protostuff/generator/java/enum.stg", rendererMap, extenderMap);
        serviceGenerator = compilerFactory.create("io/protostuff/generator/java/service.stg", rendererMap, extenderMap);
    }

    @Override
    public String getName() {
        return GENERATOR_NAME;
    }

    @Override
    public void compile(Module module) {
        messageGenerator.compile(module);
        enumGenerator.compile(module);
        serviceGenerator.compile(module);
    }
}
