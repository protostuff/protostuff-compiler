package io.protostuff.generator.java;

import io.protostuff.compiler.model.*;
import io.protostuff.generator.Formatter;

import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceUtil {
    public static String getClassName(Service service) {
        return service.getName();
    }

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
