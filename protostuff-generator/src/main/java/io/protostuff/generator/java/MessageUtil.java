package io.protostuff.generator.java;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageUtil {
    public static boolean hasFields(Message message) {
        return !message.getFields().isEmpty();
    }

    public static List<String> bitFieldNames(Message message) {
        int fieldCount = message.getFieldCount();
        if (fieldCount == 0) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        int n = (fieldCount-1) / 32 + 1;
        for (int i = 0; i < n; i++) {
            result.add("__bitField" + i);
        }
        return result;
    }
}
