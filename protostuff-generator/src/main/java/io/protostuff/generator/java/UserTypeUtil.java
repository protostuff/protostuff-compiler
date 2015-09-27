package io.protostuff.generator.java;

import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
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

    public static String getCanonicalName(UserType userType) {
        String name = getClassName(userType);
        String canonicalName;
        if (userType.isNested()) {
            Message parent = (Message) userType.getParent();
            canonicalName = getCanonicalName(parent) + '.' + name;
        } else {
            Proto proto = userType.getProto();
            String aPackage = ProtoUtil.getPackage(proto);
            if (aPackage.isEmpty()) {
                canonicalName = name;
            } else {
                canonicalName = aPackage + '.' + name;
            }
        }
        return canonicalName;
    }

}
