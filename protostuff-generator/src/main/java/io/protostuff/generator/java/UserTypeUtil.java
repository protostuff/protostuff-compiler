package io.protostuff.generator.java;

import io.protostuff.compiler.model.UserType;
import io.protostuff.generator.Formatter;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class UserTypeUtil {

    public static String getClassName(UserType userType) {
        String name = userType.getName();
        return Formatter.toPascalCase(name);
    }

}
