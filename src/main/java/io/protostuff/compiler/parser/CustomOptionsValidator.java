package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Descriptor;
import io.protostuff.compiler.model.DescriptorType;
import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CustomOptionsValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomOptionsValidator.class);

    @Override
    public void validate(ProtoContext context) {
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

        System.out.println(sourceMessage);

    }

    private Message findSourceMessage(ProtoContext context, DescriptorType type) {
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
                // Groups are not fully supported. In this place we assume that only that
                // options that are applicable for messages are also applicable for groups.
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
