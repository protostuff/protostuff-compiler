package io.protostuff.generator.java;

import io.protostuff.compiler.model.EnumConstant;
import io.protostuff.generator.Formatter;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumUtil {

    private EnumUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static String getName(EnumConstant constant) {
        String name = constant.getName();
        String underscored = Formatter.toUnderscoreCase(name);
        return Formatter.toUpperCase(underscored);
    }
}
