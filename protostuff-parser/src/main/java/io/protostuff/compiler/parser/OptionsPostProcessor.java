package io.protostuff.compiler.parser;

import static io.protostuff.compiler.parser.DefaultDescriptorProtoProvider.DESCRIPTOR_PROTO;
import static io.protostuff.compiler.parser.TypeResolverPostProcessor.createScopeLookupList;

import com.google.common.collect.ImmutableMap;
import io.protostuff.compiler.model.Descriptor;
import io.protostuff.compiler.model.DescriptorType;
import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Element;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.FieldType;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.ProtobufConstants;
import io.protostuff.compiler.model.ScalarFieldType;
import io.protostuff.compiler.model.UserTypeContainer;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Proto context post-processor for options.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class OptionsPostProcessor implements ProtoContextPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptionsPostProcessor.class);

    private static final String DEFAULT = "default";
    private static final Map<ScalarFieldType, ValueChecker> SCALAR_ASSIGNMENT_CHECKS = new EnumMap<>(ImmutableMap.<ScalarFieldType, ValueChecker>builder()
            .put(ScalarFieldType.INT32, OptionsPostProcessor::canAssignInt32)
            .put(ScalarFieldType.INT64, OptionsPostProcessor::canAssignInt64)
            .put(ScalarFieldType.UINT32, OptionsPostProcessor::canAssignUInt32)
            .put(ScalarFieldType.UINT64, OptionsPostProcessor::canAssignUInt64)
            .put(ScalarFieldType.SINT32, OptionsPostProcessor::canAssignInt32)
            .put(ScalarFieldType.SINT64, OptionsPostProcessor::canAssignInt64)
            .put(ScalarFieldType.FIXED32, OptionsPostProcessor::canAssignInt32)
            .put(ScalarFieldType.FIXED64, OptionsPostProcessor::canAssignInt64)
            .put(ScalarFieldType.SFIXED32, OptionsPostProcessor::canAssignInt32)
            .put(ScalarFieldType.SFIXED64, OptionsPostProcessor::canAssignInt64)
            .put(ScalarFieldType.FLOAT, OptionsPostProcessor::canAssignFloat)
            .put(ScalarFieldType.DOUBLE, OptionsPostProcessor::canAssignFloat)
            .put(ScalarFieldType.BOOL, OptionsPostProcessor::canAssignBool)
            .put(ScalarFieldType.STRING, OptionsPostProcessor::canAssignString)
            .put(ScalarFieldType.BYTES, OptionsPostProcessor::canAssignBytes)
            .build());

    private final Provider<ProtoContext> descriptorProtoProvider;

    @Inject
    public OptionsPostProcessor(@Named(DESCRIPTOR_PROTO) Provider<ProtoContext> descriptorProtoProvider) {
        this.descriptorProtoProvider = descriptorProtoProvider;
    }

    private static boolean canAssignInt32(DynamicMessage.Value value) {
        // TODO check if value fits into target type, for other methods as well
        return value.getType() == DynamicMessage.Value.Type.INTEGER;
    }

    private static boolean canAssignInt64(DynamicMessage.Value value) {
        return value.getType() == DynamicMessage.Value.Type.INTEGER;
    }

    private static boolean canAssignUInt32(DynamicMessage.Value value) {
        return value.getType() == DynamicMessage.Value.Type.INTEGER;
    }

    private static boolean canAssignUInt64(DynamicMessage.Value value) {
        return value.getType() == DynamicMessage.Value.Type.INTEGER;
    }

    private static boolean canAssignFloat(DynamicMessage.Value value) {
        return value.getType() == DynamicMessage.Value.Type.INTEGER
                || value.getType() == DynamicMessage.Value.Type.FLOAT;
    }

    private static boolean canAssignBool(DynamicMessage.Value value) {
        return value.getType() == DynamicMessage.Value.Type.BOOLEAN;
    }

    private static boolean canAssignString(DynamicMessage.Value value) {
        return value.getType() == DynamicMessage.Value.Type.STRING;
    }

    private static boolean canAssignBytes(DynamicMessage.Value value) {
        return value.getType() == DynamicMessage.Value.Type.STRING;
    }

    @Override
    public void process(ProtoContext context) {
        ProtoWalker.newInstance(context)
                .onProto(this::processOptions)
                .onMessage(this::processOptions)
                .onField(this::processOptions)
                .onEnum(this::processOptions)
                .onEnumConstant(this::processOptions)
                .onService(this::processOptions)
                .onServiceMethod(this::processOptions)
                .onOneof(this::processOptions)
                .walk();
    }

    private void processOptions(ProtoContext context, Descriptor descriptor) {
        DynamicMessage options = descriptor.getOptions();
        if (options.isEmpty()) {
            // nothing to check - skip this message
            return;
        }
        String descriptorClassName = descriptor.getClass().getSimpleName();
        String descriptorName = descriptor.getName();
        LOGGER.trace("processing class={} name={}", descriptorClassName, descriptorName);
        Message sourceMessage = findSourceMessage(context, descriptor.getDescriptorType());
        processOptions(context, sourceMessage, descriptor, options);
    }

    private void processOptions(ProtoContext context, Message sourceMessage, Descriptor owningDescriptor, DynamicMessage options) {
        processCustomOptions(context, sourceMessage, owningDescriptor, options);
        processStandardOptions(context, sourceMessage, owningDescriptor, options);
    }

    private void processStandardOptions(ProtoContext context, Message sourceMessage, Descriptor owningDescriptor, DynamicMessage options) {
        for (Map.Entry<DynamicMessage.Key, DynamicMessage.Value> entry : options.getFields()) {
            DynamicMessage.Key key = entry.getKey();
            DynamicMessage.Value value = entry.getValue();
            if (key.isExtension()) {
                continue;
            }
            // check standard option
            String fieldName = key.getName();
            Field field = sourceMessage.getField(fieldName);
            if (DEFAULT.equals(fieldName)) {
                // TODO: check value of default option
            } else {
                if (field == null) {
                    throw new ParserException(value, "Unknown option: '%s'", fieldName);
                }
                checkFieldValue(context, owningDescriptor, field, value);
            }

        }
    }

    private void processCustomOptions(ProtoContext context, Message sourceMessage, Descriptor owningDescriptor, DynamicMessage options) {
        ExtensionRegistry extensionRegistry = context.getExtensionRegistry();
        Map<String, Field> extensionFields = extensionRegistry.getExtensionFields(sourceMessage);
        Map<DynamicMessage.Key, String> fullyQualifiedNames = new HashMap<>();
        for (Map.Entry<DynamicMessage.Key, DynamicMessage.Value> entry : options.getFields()) {
            DynamicMessage.Key key = entry.getKey();
            DynamicMessage.Value value = entry.getValue();
            if (!key.isExtension()) {
                continue;
            }
            String fullyQualifiedName = getFullyQualifiedName(owningDescriptor, extensionFields, key, value);
            Field extensionField = extensionFields.get(fullyQualifiedName);
            fullyQualifiedNames.put(key, fullyQualifiedName);
            checkFieldValue(context, owningDescriptor, extensionField, value);
        }
        for (Map.Entry<DynamicMessage.Key, String> entry : fullyQualifiedNames.entrySet()) {
            options.normalizeName(entry.getKey(), entry.getValue());
        }
    }

    private String getFullyQualifiedName(Descriptor owningDescriptor, Map<String, Field> extensionFields, DynamicMessage.Key key, DynamicMessage.Value value) {
        if (key.getName().startsWith(".")) {
            String name = key.getName();
            if (extensionFields.containsKey(name)) {
                return name;
            }
        } else {
            UserTypeContainer owningContainer = getOwningContainer(owningDescriptor);
            Deque<String> scopeLookupList = createScopeLookupList(owningContainer);
            for (String scope : scopeLookupList) {
                String name = scope + key.getName();
                if (extensionFields.containsKey(name)) {
                    return name;
                }
            }
        }
        throw new ParserException(value, "Unknown option: '%s'", key.getName());
    }

    private UserTypeContainer getOwningContainer(Descriptor descriptor) {
        Element tmp = descriptor;
        while (!(tmp instanceof UserTypeContainer)) {
            tmp = tmp.getParent();
        }
        return (UserTypeContainer) tmp;
    }

    private void checkFieldValue(ProtoContext context, Descriptor descriptor, Field field, DynamicMessage.Value value) {
        String fieldName = field.getName();
        FieldType fieldType = field.getType();
        DynamicMessage.Value.Type valueType = value.getType();
        if (fieldType instanceof ScalarFieldType) {
            ScalarFieldType scalarFieldType = (ScalarFieldType) fieldType;
            if (!isAssignableFrom(scalarFieldType, value)) {
                throw new ParserException(value, "Cannot set option '%s': expected %s value", fieldName, fieldType);
            }
        } else if (fieldType instanceof Enum) {
            Enum anEnum = (Enum) fieldType;
            Set<String> allowedNames = anEnum.getConstantNames();
            if (valueType != DynamicMessage.Value.Type.ENUM
                    || !allowedNames.contains(value.getEnumName())) {
                throw new ParserException(value, "Cannot set option '%s': expected enum = %s", fieldName, allowedNames);
            }
        } else if (fieldType instanceof Message) {
            if (valueType != DynamicMessage.Value.Type.MESSAGE) {
                throw new ParserException(value, "Cannot set option '%s': expected message value", fieldName);
            }
            Message message = (Message) fieldType;
            processOptions(context, message, descriptor, value.getMessage());
        } else {
            throw new IllegalStateException("Unknown field type: " + fieldType);
        }
    }

    private boolean isAssignableFrom(ScalarFieldType target, DynamicMessage.Value value) {
        ValueChecker checker = SCALAR_ASSIGNMENT_CHECKS.get(target);
        if (checker == null) {
            throw new IllegalStateException("Unknown field type: " + target);
        }
        return checker.apply(value);
    }

    private Message findSourceMessage(ProtoContext context, DescriptorType type) {
        Message message = tryResolveFromContext(context, type);
        if (message == null) {
            // There might be no import for "google/protobuf/descriptor.proto"
            // in the source file, so it is not possible to load standard options
            // descriptors using normal way. We should try to lookup directly.
            ProtoContext descriptorProto = descriptorProtoProvider.get();
            return tryResolveFromContext(descriptorProto, type);
        }
        return message;
    }

    private Message tryResolveFromContext(ProtoContext context, DescriptorType type) {
        switch (type) {
            case PROTO:
                return context.resolve(Message.class, ProtobufConstants.MSG_FILE_OPTIONS);
            case ENUM:
                return context.resolve(Message.class, ProtobufConstants.MSG_ENUM_OPTIONS);
            case ENUM_CONSTANT:
                return context.resolve(Message.class, ProtobufConstants.MSG_ENUM_VALUE_OPTIONS);
            case MESSAGE:
            case GROUP:
                // Groups are not fully supported. For simplicity, in this place we assume
                // that only that options that are applicable for messages are also
                // applicable for groups.
                // But, actually it is invalid assumption, because both field and message
                // options are applicable to groups.
                return context.resolve(Message.class, ProtobufConstants.MSG_MESSAGE_OPTIONS);
            case MESSAGE_FIELD:
                return context.resolve(Message.class, ProtobufConstants.MSG_FIELD_OPTIONS);
            case SERVICE:
                return context.resolve(Message.class, ProtobufConstants.MSG_SERVICE_OPTIONS);
            case SERVICE_METHOD:
                return context.resolve(Message.class, ProtobufConstants.MSG_METHOD_OPTIONS);
            case ONEOF:
                return context.resolve(Message.class, ProtobufConstants.MSG_ONEOF_OPTIONS);
            default:
                throw new IllegalStateException("Unknown descriptor type: " + type);
        }
    }

    @FunctionalInterface
    interface ValueChecker extends Function<DynamicMessage.Value, Boolean> {
    }
}
