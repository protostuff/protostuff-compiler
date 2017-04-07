package io.protostuff.generator.java;

import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.UserType;
import io.protostuff.generator.Formatter;

/**
 * Custom properties for user types - messages and enums, used by java code generator.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class UserTypeUtil {

    private UserTypeUtil() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Returns a java class name for a user type.
     */
    public static String getClassName(UserType userType) {
        String name = userType.getName();
        return Formatter.toPascalCase(name);
    }

    /**
     * Returns java canonical class name for a user type.
     */
    public static String getCanonicalName(UserType userType) {
        String name = getClassName(userType);
        String canonicalName;
        if (userType.isNested()) {
            Message parent = (Message) userType.getParent();
            canonicalName = getCanonicalName(parent) + '.' + name;
        } else {
            Proto proto = userType.getProto();
            String pkg = ProtoUtil.getPackage(proto);
            if (pkg.isEmpty()) {
                canonicalName = name;
            } else {
                canonicalName = pkg + '.' + name;
            }
        }
        return canonicalName;
    }

}
