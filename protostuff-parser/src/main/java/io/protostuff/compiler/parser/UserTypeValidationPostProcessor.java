package io.protostuff.compiler.parser;

import com.google.common.annotations.VisibleForTesting;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Range;

import java.util.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class UserTypeValidationPostProcessor implements ProtoContextPostProcessor {

    private final int MIN_TAG = 1;
    private final int MAX_TAG = Field.MAX_TAG_VALUE;
    private final int SYS_RESERVED_START = 19000;
    private final int SYS_RESERVED_END = 19999;

    @Override
    public void process(ProtoContext context) {
        ProtoWalker.newInstance(context)
                .onMessage(this::processMessage)
                .walk();
    }

    private void processMessage(ProtoContext context, Message message) {
        List<Field> fields = message.getFields();
        checkInvalidTags(fields);
        checkDuplicateTags(fields);
        checkDuplicateNames(fields);
        checkReservedTags(message, fields);
        checkReservedNames(message, fields);
    }

    private void checkReservedTags(Message message, List<Field> fields) {
        List<Range> ranges = message.getReservedFieldRanges();
        for (Field field : fields) {
            int tag = field.getTag();
            for (Range range : ranges) {
                if (range.contains(tag)) {
                    throw new ParserException(field, "Reserved field tag: %d", tag);
                }
            }
        }
    }

    private void checkReservedNames(Message message, List<Field> fields) {
        Set<String> names = new HashSet<>(message.getReservedFieldNames());
        for (Field field : fields) {
            String name = field.getName();
            if (names.contains(name)) {
                throw new ParserException(field, "Reserved field name: '%s'", name);
            }
        }
    }

    private void checkInvalidTags(List<Field> fields) {
        for (Field field : fields) {
            int tag = field.getTag();
            if (!isValidTagValue(tag)) {
                throw new ParserException(field, "Invalid tag: %d, allowed range is [%d, %d) and (%d, %d]",
                        tag, MIN_TAG, SYS_RESERVED_START, SYS_RESERVED_END, MAX_TAG);
            }
        }
    }

    @VisibleForTesting
    boolean isValidTagValue(int tag) {
        return tag >= MIN_TAG && tag <= MAX_TAG
                && !(tag >= SYS_RESERVED_START && tag <= SYS_RESERVED_END);
    }

    private void checkDuplicateTags(List<Field> fields) {
        Map<Integer, Field> fieldByTag = new HashMap<>();
        for (Field field : fields) {
            int tag = field.getTag();
            if (fieldByTag.containsKey(tag)) {
                throw new ParserException(field, "Duplicate field tag: %d", tag);
            }
            fieldByTag.put(tag, field);
        }
    }

    private void checkDuplicateNames(List<Field> fields) {
        Map<String, Field> fieldByName = new HashMap<>();
        for (Field field : fields) {
            String name = field.getName();
            if (fieldByName.containsKey(name)) {
                throw new ParserException(field, "Duplicate field name: '%s'", name);
            }
            fieldByName.put(name, field);
        }
    }

}
