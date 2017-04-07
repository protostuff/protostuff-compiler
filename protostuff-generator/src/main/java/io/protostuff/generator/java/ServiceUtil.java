package io.protostuff.generator.java;

import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import io.protostuff.generator.Formatter;

/**
 * Custom service properties for java code generator.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceUtil {

    private ServiceUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static String getClassName(Service service) {
        String name = service.getName();
        return Formatter.toPascalCase(name);
    }

    /**
     * Returns java method name for corresponding rpc method.
     */
    public static String getMethodName(ServiceMethod serviceMethod) {
        String name = serviceMethod.getName();
        String formattedName = Formatter.toCamelCase(name);
        if (isReservedKeyword(formattedName)) {
            return formattedName + '_';
        }
        return formattedName;
    }

    private static boolean isReservedKeyword(String formattedName) {
        return JavaConstants.RESERVED_KEYWORDS.contains(formattedName);
    }

}
