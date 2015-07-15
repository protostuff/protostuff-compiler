package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.GroupContainer;
import io.protostuff.compiler.model.Map;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.UserType;
import io.protostuff.compiler.model.UserTypeContainer;

import java.util.ArrayList;
import java.util.Collections;
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
        List<Message> messages = new ArrayList<>();
        messages.addAll(proto.getMessages());
        for (Extension extension : proto.getDeclaredExtensions()) {
            messages.addAll(extension.getGroups());
        }
        for (Message type : messages) {
            type.setProto(proto);
            String fullName = proto.getNamespace() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
            for (Map map : type.getMaps()) {
                map.setProto(proto);
                map.setFullName(type.getNamespace() + map.getName());
            }
        }

        List<io.protostuff.compiler.model.Enum> enums = proto.getEnums();
        for (Enum type : enums) {
            type.setProto(proto);
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
        List<Message> nestedMessages = new ArrayList<>();
        nestedMessages.addAll(parent.getMessages());
        if (parent instanceof GroupContainer) {
            nestedMessages.addAll(((GroupContainer)parent).getGroups());
        }
        for (Extension extension : parent.getDeclaredExtensions()) {
            nestedMessages.addAll(extension.getGroups());
        }

        List<Enum> nestedEnums = parent.getEnums();
        Consumer<UserType> nestedTypeProcessor = type -> {
            type.setProto(context.getProto());
            String fullName = parent.getNamespace() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        };
        nestedEnums.forEach(nestedTypeProcessor);
        nestedMessages.forEach(nestedTypeProcessor);
        nestedMessages.forEach(message -> {
            for (Map map : message.getMaps()) {
                map.setProto(message.getProto());
                map.setFullName(message.getNamespace() + map.getName());
            }
            registerNestedUserTypes(context, message);
        });
    }
}
