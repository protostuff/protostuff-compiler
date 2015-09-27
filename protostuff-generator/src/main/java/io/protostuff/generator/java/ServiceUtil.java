package io.protostuff.generator.java;

import io.protostuff.compiler.model.Service;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceUtil {
    public static String getClassName(Service service) {
        return service.getName();
    }
}
