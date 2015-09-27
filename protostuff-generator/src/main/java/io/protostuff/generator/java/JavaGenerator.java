package io.protostuff.generator.java;

import com.google.common.collect.ImmutableMap;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.inject.Inject;

import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.EnumConstant;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.ScalarFieldType;
import io.protostuff.compiler.model.Service;
import io.protostuff.generator.ObjectExtender;
import io.protostuff.generator.ProtoCompiler;
import io.protostuff.generator.SimpleObjectExtender;
import io.protostuff.generator.StCompilerFactory;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JavaGenerator implements ProtoCompiler {
    public static final String GENERATOR_NAME = "java";

    public static final Function<Proto, String> JAVA_PACKAGE = proto -> {
        DynamicMessage.Value javaPackage = proto.getOptions().get("java_package");
        if (javaPackage != null) {
            return javaPackage.getString();
        }
        return proto.getPackage().getValue();
    };

    public static final Function<Proto, String> JAVA_PACKAGE_PATH = proto -> {
        String javaPackage = JAVA_PACKAGE.apply(proto);
        return javaPackage.replace('.', '/');
    };

    private final StCompilerFactory compilerFactory;
    private final ProtoCompiler messageGenerator;
    private final ProtoCompiler enumGenerator;

    @Inject
    public JavaGenerator(StCompilerFactory compilerFactory) {
        this.compilerFactory = compilerFactory;
        // TODO initialization should be lazy - usually only one generator is used
        Map<Class<?>, AttributeRenderer> rendererMap = new HashMap<>();
        Map<Class<?>, ObjectExtender<?>> extenderMap = ImmutableMap.<Class<?>, ObjectExtender<?>>builder()
                .put(Proto.class, SimpleObjectExtender.<Proto>newBuilder()
                        .property("javaPackage", ProtoUtil::getPackage)
                        .property("javaPackagePath", ProtoUtil::getPackagePath)
                        .build())
                .put(ScalarFieldType.class, SimpleObjectExtender.<ScalarFieldType>newBuilder()
                        .property("javaWrapperType", ScalarFieldTypeUtil::getWrapperType)
                        .property("javaPrimitiveType", ScalarFieldTypeUtil::getPrimitiveType)
                        .build())
                .put(Message.class, SimpleObjectExtender.<Message>newBuilder()
                        .property("javaName", UserTypeUtil::getClassName)
                        .build())
                .put(Field.class, SimpleObjectExtender.<Field>newBuilder()
                        .property("javaType", MessageFieldUtil::getFieldType)
                        .property("javaName", MessageFieldUtil::getFieldName)
                        .build())
                .put(Enum.class, SimpleObjectExtender.<Enum>newBuilder()
                        .property("javaName", UserTypeUtil::getClassName)
                        .build())
                .put(EnumConstant.class, SimpleObjectExtender.<EnumConstant>newBuilder()
                        .property("javaName", EnumUtil::getName)
                        .build())
                .put(Service.class, SimpleObjectExtender.<Service>newBuilder()
                        .property("javaName", ServiceUtil::getClassName)
                        .build())
                .build();
        messageGenerator = compilerFactory.create("io/protostuff/generator/java/message.stg", rendererMap, extenderMap);
        enumGenerator = compilerFactory.create("io/protostuff/generator/java/enum.stg", rendererMap, extenderMap);
    }

    @Override
    public String getName() {
        return GENERATOR_NAME;
    }

    @Override
    public void compile(Module module) {
        messageGenerator.compile(module);
        enumGenerator.compile(module);
    }
}
