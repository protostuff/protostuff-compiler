package io.protostuff.generator.java;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.inject.Inject;

import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
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
    private final ProtoCompiler delegate;

    @Inject
    public JavaGenerator(StCompilerFactory compilerFactory) {
        this.compilerFactory = compilerFactory;
        // TODO initialization should be lazy - usually only one generator is used
        Map<Class<?>, AttributeRenderer> rendererMap = new HashMap<>();
        Map<Class<?>, ObjectExtender<?>> extenderMap = new HashMap<>();
        extenderMap.put(Proto.class, SimpleObjectExtender.<Proto>newBuilder()
                .property("javaPackage", JavaGenerator::javaPackage)
                .property("javaPackagePath", JavaGenerator::javaPackagePath)
                .build());
        delegate = compilerFactory.create("io/protostuff/generator/java/message.stg", rendererMap, extenderMap);
    }

    public static String javaPackage(Proto proto) {
        DynamicMessage.Value javaPackage = proto.getOptions().get("java_package");
        if (javaPackage != null) {
            return javaPackage.getString();
        }
        return proto.getPackage().getValue();
    }

    public static String javaPackagePath(Proto proto) {
        String javaPackage = javaPackage(proto);
        return javaPackage.replace('.', '/');
    }

    @Override
    public String getName() {
        return GENERATOR_NAME;
    }

    ;

    @Override
    public void compile(Module module) {
        delegate.compile(module);
    }

    ;
}
