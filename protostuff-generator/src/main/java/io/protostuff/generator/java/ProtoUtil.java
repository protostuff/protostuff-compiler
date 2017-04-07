package io.protostuff.generator.java;

import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Proto;

/**
 * Custom proto node properties for java code generator.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoUtil {

    public static final String OPTION_JAVA_PACKAGE = "java_package";

    private ProtoUtil() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Returns java package name.
     */
    public static String getPackage(Proto proto) {
        DynamicMessage.Value javaPackage = proto.getOptions().get(OPTION_JAVA_PACKAGE);
        if (javaPackage != null) {
            return javaPackage.getString();
        }
        return proto.getPackage().getValue();
    }

    /**
     * Returns a relative path where java class should be placed,
     * computed from java package.
     */
    public static String getPackagePath(Proto proto) {
        String javaPackage = getPackage(proto);
        return javaPackage.replace('.', '/');
    }
}
