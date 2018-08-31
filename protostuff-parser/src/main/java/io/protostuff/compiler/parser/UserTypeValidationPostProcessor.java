package io.protostuff.compiler.parser;

import com.google.common.annotations.VisibleForTesting;
import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.EnumConstant;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Range;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validation post-processor for user types.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class UserTypeValidationPostProcessor implements ProtoContextPostProcessor {

    private static final String ALLOW_ALIAS = "allow_alias";
    private static final int MIN_TAG = 1;
    private static final int MAX_TAG = Field.MAX_TAG_VALUE;
    private static final int SYS_RESERVED_START = 19000;
    private static final int SYS_RESERVED_END = 19999;

    @Override
    public void process(ProtoContext context) {
        ProtoWalker.newInstance(context)
                .onMessage(this::processMessage)
                .onEnum(this::processEnum)
                .onService(this::processService)
                .walk();
    }

    private void processService(Service service) {
        List<ServiceMethod> methods = service.getMethods();
        checkDuplicateServiceMethodNames(methods);
    }

    private void checkDuplicateServiceMethodNames(List<ServiceMethod> methods) {
        Map<String, ServiceMethod> methodsByName = new HashMap<>();
        for (ServiceMethod method : methods) {
            String name = method.getName();
            if (methodsByName.containsKey(name)) {
                throw new ParserException(method, "Duplicate service method name: '%s'", name);
            }
            methodsByName.put(name, method);
        }
    }

    private void processEnum(Enum anEnum) {
        List<EnumConstant> constants = anEnum.getConstants();
        checkDuplicateEnumConstantNames(constants);
        checkDuplicateEnumConstantValues(anEnum, constants);
        checkReservedEnumTags(anEnum, constants);
        checkReservedEnumNames(anEnum, constants);
    }

    private void checkReservedEnumNames(Enum anEnum, List<EnumConstant> constants) {
        Set<String> names = new HashSet<>(anEnum.getReservedFieldNames());
        for (EnumConstant constant : constants) {
            String name = constant.getName();
            if (names.contains(name)) {
                throw new ParserException(constant, "Reserved enum name: '%s'", name);
            }
        }
    }

    private void checkReservedEnumTags(Enum anEnum, List<EnumConstant> constants) {
        List<Range> ranges = anEnum.getReservedFieldRanges();
        for (EnumConstant constant : constants) {
            int tag = constant.getValue();
            for (Range range : ranges) {
                if (range.contains(tag)) {
                    throw new ParserException(constant, "Reserved enum tag: %d", tag);
                }
            }
        }
    }

    private void checkDuplicateEnumConstantValues(Enum anEnum, List<EnumConstant> constants) {
        DynamicMessage.Value allowAlias = anEnum.getOptions().get(ALLOW_ALIAS);
        if (allowAlias != null
                && allowAlias.isBooleanType()
                && allowAlias.getBoolean()) {
            // skip this check if aliases are allowed
            return;
        }
        Map<Integer, EnumConstant> constantByValue = new HashMap<>();
        for (EnumConstant constant : constants) {
            Integer value = constant.getValue();
            if (constantByValue.containsKey(value)) {
                throw new ParserException(constant, "Duplicate enum constant value: %d", value);
            }
            constantByValue.put(value, constant);
        }
    }

    private void checkDuplicateEnumConstantNames(List<EnumConstant> constants) {
        Map<String, EnumConstant> constantByName = new HashMap<>();
        for (EnumConstant constant : constants) {
            String name = constant.getName();
            if (constantByName.containsKey(name)) {
                throw new ParserException(constant, "Duplicate enum constant name: '%s'", name);
            }
            constantByName.put(name, constant);
        }
    }

    private void processMessage(Message message) {
        List<Field> fields = message.getFields();
        checkInvalidFieldTags(fields);
        checkDuplicateFieldTags(fields);
        checkDuplicateFieldNames(fields);
        checkReservedFieldTags(message, fields);
        checkReservedFieldNames(message, fields);
        checkFieldModifier(message, fields);
    }

    private void checkFieldModifier(Message message, List<Field> fields) {
        for (Field field : fields) {
            if (field.isOneofPart() && field.hasModifier()) {
                throw new ParserException(field, "Oneof field cannot have modifier: %s", field.getModifier());
            }
        }
    }

    private void checkReservedFieldTags(Message message, List<Field> fields) {
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

    private void checkReservedFieldNames(Message message, List<Field> fields) {
        Set<String> names = new HashSet<>(message.getReservedFieldNames());
        for (Field field : fields) {
            String name = field.getName();
            if (names.contains(name)) {
                throw new ParserException(field, "Reserved field name: '%s'", name);
            }
        }
    }

    private void checkInvalidFieldTags(List<Field> fields) {
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

    private void checkDuplicateFieldTags(List<Field> fields) {
        Map<Integer, Field> fieldByTag = new HashMap<>();
        for (Field field : fields) {
            int tag = field.getTag();
            if (fieldByTag.containsKey(tag)) {
                throw new ParserException(field, "Duplicate field tag: %d", tag);
            }
            fieldByTag.put(tag, field);
        }
    }

    private void checkDuplicateFieldNames(List<Field> fields) {
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
