package io.protostuff.compiler.parser;

import com.google.common.annotations.VisibleForTesting;
import io.protostuff.compiler.model.*;
import io.protostuff.compiler.model.Enum;

import java.util.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class UserTypeValidationPostProcessor implements ProtoContextPostProcessor {

    public static final String ALLOW_ALIAS = "allow_alias";
    private final int MIN_TAG = 1;
    private final int MAX_TAG = Field.MAX_TAG_VALUE;
    private final int SYS_RESERVED_START = 19000;
    private final int SYS_RESERVED_END = 19999;

    @Override
    public void process(ProtoContext context) {
        ProtoWalker.newInstance(context)
                .onMessage(new ProtoWalker.Processor<Message>() {
                    @Override
                    public void run(ProtoContext context, Message message) {
                        processMessage(context, message);
                    }
                })
                .onEnum(new ProtoWalker.Processor<Enum>() {
                    @Override
                    public void run(ProtoContext context, Enum anEnum) {
                        processEnum(context, anEnum);
                    }
                })
                .onService(new ProtoWalker.Processor<Service>() {
                    @Override
                    public void run(ProtoContext context, Service service) {
                        processService(context, service);
                    }
                })
                .walk();
    }

    private void processService(ProtoContext context, Service service) {
        List<ServiceMethod> methods = service.getMethods();
        checkDuplicateServiceMethodNames(methods);
    }

    private void checkDuplicateServiceMethodNames(List<ServiceMethod> methods) {
        Map<String, ServiceMethod> methodsByName = new HashMap<String, ServiceMethod>();
        for (ServiceMethod method : methods) {
            String name = method.getName();
            if (methodsByName.containsKey(name)) {
                throw new ParserException(method, "Duplicate service method name: '%s'", name);
            }
            methodsByName.put(name, method);
        }
    }

    private void processEnum(ProtoContext context, Enum anEnum) {
        List<EnumConstant> constants = anEnum.getConstants();
        checkDuplicateEnumConstantNames(constants);
        checkDuplicateEnumConstantValues(anEnum, constants);
    }

    private void checkDuplicateEnumConstantValues(Enum anEnum, List<EnumConstant> constants) {
        DynamicMessage.Value allowAlias = anEnum.getOptions().get(ALLOW_ALIAS);
        if (allowAlias != null
                && allowAlias.isBooleanType()
                && allowAlias.getBoolean()) {
            // skip this check if aliases are allowed
            return;
        }
        Map<Integer, EnumConstant> constantByValue = new HashMap<Integer, EnumConstant>();
        for (EnumConstant constant : constants) {
            Integer value = constant.getValue();
            if (constantByValue.containsKey(value)) {
                throw new ParserException(constant, "Duplicate enum constant value: %d", value);
            }
            constantByValue.put(value, constant);
        }
    }

    private void checkDuplicateEnumConstantNames(List<EnumConstant> constants) {
        Map<String, EnumConstant> constantByName = new HashMap<String, EnumConstant>();
        for (EnumConstant constant : constants) {
            String name = constant.getName();
            if (constantByName.containsKey(name)) {
                throw new ParserException(constant, "Duplicate enum constant name: '%s'", name);
            }
            constantByName.put(name, constant);
        }
    }

    private void processMessage(ProtoContext context, Message message) {
        List<Field> fields = message.getFields();
        checkInvalidFieldTags(fields);
        checkDuplicateFieldTags(fields);
        checkDuplicateFieldNames(fields);
        checkReservedFieldTags(message, fields);
        checkReservedFieldNames(message, fields);
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
        Set<String> names = new HashSet<String>(message.getReservedFieldNames());
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
        Map<Integer, Field> fieldByTag = new HashMap<Integer, Field>();
        for (Field field : fields) {
            int tag = field.getTag();
            if (fieldByTag.containsKey(tag)) {
                throw new ParserException(field, "Duplicate field tag: %d", tag);
            }
            fieldByTag.put(tag, field);
        }
    }

    private void checkDuplicateFieldNames(List<Field> fields) {
        Map<String, Field> fieldByName = new HashMap<String, Field>();
        for (Field field : fields) {
            String name = field.getName();
            if (fieldByName.containsKey(name)) {
                throw new ParserException(field, "Duplicate field name: '%s'", name);
            }
            fieldByName.put(name, field);
        }
    }

}
