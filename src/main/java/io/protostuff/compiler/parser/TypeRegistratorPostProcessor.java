package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.*;
import io.protostuff.compiler.model.Enum;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class TypeRegistratorPostProcessor implements ProtoContextPostProcessor {

    @Override
    public void process(ProtoContext context) {
        registerUserTypes(context);
    }

    private void registerUserTypes(ProtoContext context) {
        final Proto proto = context.getProto();
        List<Message> messages = proto.getMessages();
        for (Message type : messages) {
            type.setProto(proto);
            type.setParent(proto);
            String fullName = proto.getNamespace() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        }

        List<io.protostuff.compiler.model.Enum> enums = proto.getEnums();
        for (Enum type : enums) {
            type.setProto(proto);
            type.setParent(proto);
            String fullName = proto.getNamespace() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        }

        List<Service> services = proto.getServices();
        for (Service type : services) {
            type.setProto(proto);
            String fullName = proto.getNamespace() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        }

        for (Message message : messages) {
            registerNestedUserTypes(context, message);
        }

    }

    private void registerNestedUserTypes(ProtoContext context, UserTypeContainer parent) {
        List<Message> nestedMessages = parent.getMessages();
        List<Enum> nestedEnums = parent.getEnums();
        Consumer<UserFieldType> nestedTypeProcessor = type -> {
            type.setProto(context.getProto());
            type.setParent(parent);
            String fullName = parent.getNamespace() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        };
        nestedEnums.forEach(nestedTypeProcessor);
        nestedMessages.forEach(nestedTypeProcessor);
        nestedMessages.forEach(message -> registerNestedUserTypes(context, message));
    }
}
