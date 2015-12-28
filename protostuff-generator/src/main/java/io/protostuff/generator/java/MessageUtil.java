package io.protostuff.generator.java;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageUtil {
    public static boolean hasFields(Message message) {
        return !message.getFields().isEmpty();
    }
}
