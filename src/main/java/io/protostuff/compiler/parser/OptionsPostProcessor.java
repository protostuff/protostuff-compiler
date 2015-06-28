package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.*;
import io.protostuff.compiler.model.Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.List;
import java.util.Map;

import static io.protostuff.compiler.parser.DefaultDescriptorProtoProvider.DESCRIPTOR_PROTO;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class OptionsPostProcessor implements ProtoPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptionsPostProcessor.class);

    private final Provider<ProtoContext> descriptorProtoProvider;

    @Inject
    public OptionsPostProcessor(@Named(DESCRIPTOR_PROTO) Provider<ProtoContext> descriptorProtoProvider) {
        this.descriptorProtoProvider = descriptorProtoProvider;
    }

    @Override
    public void process(ProtoContext context) {
        ProtoWalker.newInstance(context)
                .onProto(this::checkOptions)
                .onMessage(this::checkOptions)
                .walk();
    }

    private void checkOptions(ProtoContext context, Descriptor descriptor) {
        DynamicMessage options = descriptor.getOptions();
        if (options.isEmpty()) {
            // nothing to check - skip this message
            return;
        }
        String descriptorClassName = descriptor.getClass().getSimpleName();
        String descriptorName = descriptor.getName();
        LOGGER.trace("processing class={} name={}", descriptorClassName, descriptorName);
        Message sourceMessage = findSourceMessage(context, descriptor.getDescriptorType());

        if (sourceMessage == null) {
            // embedded descriptor.proto is not yet loaded
            return;
        }

        for (Map.Entry<DynamicMessage.Key, DynamicMessage.Value> entry : options.getFields()) {
            DynamicMessage.Key key = entry.getKey();
            DynamicMessage.Value value = entry.getValue();
            if (key.isExtension()) {
                // TODO check extension
            } else {
                // check standard option
                String fieldName = key.getName();
                Field field = sourceMessage.getField(fieldName);
                if (field == null) {
                    throw new ParserException(value, "Unknown option: %s", fieldName);
                }
                checkFieldValue(field, value);
            }
        }
    }

    private void checkFieldValue(Field field, DynamicMessage.Value value) {
        FieldType fieldType = field.getType();
        DynamicMessage.Value.Type valueType = value.getType();
        if (!isAssignableFrom(fieldType, valueType)) {
            throw new ParserException(value, "Can not assign %s to %s: incompatible types", valueType, fieldType);
        }
    }

    private boolean isAssignableFrom(FieldType fieldType, DynamicMessage.Value.Type valueType) {
        if (fieldType instanceof ScalarFieldType) {
            ScalarFieldType target = (ScalarFieldType) fieldType;
            switch (target) {
                case INT32:
                case INT64:
                case UINT32:
                case UINT64:
                case SINT32:
                case SINT64:
                case FIXED32:
                case FIXED64:
                case SFIXED32:
                case SFIXED64:
                    // TODO check if value fits into target type
                    return valueType == DynamicMessage.Value.Type.INTEGER;
                case FLOAT:
                case DOUBLE:
                    // TODO check if value fits into target type
                    return valueType == DynamicMessage.Value.Type.INTEGER
                            || valueType == DynamicMessage.Value.Type.FLOAT;
                case BOOL:
                    return valueType == DynamicMessage.Value.Type.BOOLEAN;
                case STRING:
                    return valueType == DynamicMessage.Value.Type.STRING;
                case BYTES:
                    return valueType == DynamicMessage.Value.Type.STRING;
                default:
                    throw new IllegalStateException("Unknown field type: " + target);
            }
        } else if (fieldType instanceof Enum) {
            return valueType == DynamicMessage.Value.Type.ENUM;
        } else if (fieldType instanceof Message) {
            return valueType == DynamicMessage.Value.Type.MESSAGE;
        } else if (fieldType instanceof Group) {
            return valueType == DynamicMessage.Value.Type.MESSAGE;
        }
        throw new IllegalStateException("Unknown field type: " + fieldType);
    }

    private Message findSourceMessage(ProtoContext context, DescriptorType type) {
        Message message = tryResolveFromContext(context, type);
        if (message == null) {
            ProtoContext descriptorProto = descriptorProtoProvider.get();
            return tryResolveFromContext(descriptorProto, type);
        }
        return message;
    }

    private Message tryResolveFromContext(ProtoContext context, DescriptorType type) {
        switch (type) {
            case PROTO:
                return context.resolve(Message.class, ".google.protobuf.FileOptions");
            case ENUM:
                return context.resolve(Message.class, ".google.protobuf.EnumOptions");
            case ENUM_CONSTANT:
                return context.resolve(Message.class, ".google.protobuf.EnumValueOptions");
            case MESSAGE:
                return context.resolve(Message.class, ".google.protobuf.MessageOptions");
            case MESSAGE_FIELD:
                return context.resolve(Message.class, ".google.protobuf.FieldOptions");
            case GROUP:
                // Groups are not fully supported. For simplicity, in this place we assume
                // that only that options that are applicable for messages are also
                // applicable for groups.
                // But, actually it is invalid assumption, because both field and message
                // options are applicable to groups.
                return context.resolve(Message.class, ".google.protobuf.MessageOptions");
            case SERVICE:
                return context.resolve(Message.class, ".google.protobuf.ServiceOptions");
            case SERVICE_METHOD:
                return context.resolve(Message.class, ".google.protobuf.MethodOptions");
            default:
                throw new IllegalStateException("Unknown descriptor type: " + type);
        }
    }
}
