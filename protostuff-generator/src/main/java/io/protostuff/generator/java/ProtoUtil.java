package io.protostuff.generator.java;

import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Proto;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoUtil {

    public static final String OPTION_JAVA_PACKAGE = "java_package";

    public static String getPackage(Proto proto) {
        DynamicMessage.Value javaPackage = proto.getOptions().get(OPTION_JAVA_PACKAGE);
        if (javaPackage != null) {
            return javaPackage.getString();
        }
        return proto.getPackage().getValue();
    }

    public static String getPackagePath(Proto proto) {
        String javaPackage = getPackage(proto);
        return javaPackage.replace('.', '/');
    }
}
