package io.protostuff.compiler.parser;

import io.protostuff.compiler.Visitor;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.*;

import java.util.ArrayList;
import java.util.List;

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
        List<Message> messages = new ArrayList<Message>();
        messages.addAll(proto.getMessages());
        for (Extension extension : proto.getDeclaredExtensions()) {
            messages.addAll(extension.getGroups());
        }
        for (Message type : messages) {
            type.setProto(proto);
            String fullyQualifiedName = proto.getNamespace() + type.getName();
            type.setFullyQualifiedName(fullyQualifiedName);
            context.register(fullyQualifiedName, type);
        }

        List<io.protostuff.compiler.model.Enum> enums = proto.getEnums();
        for (Enum type : enums) {
            type.setProto(proto);
            String fullyQualifiedName = proto.getNamespace() + type.getName();
            type.setFullyQualifiedName(fullyQualifiedName);
            context.register(fullyQualifiedName, type);
        }

        List<Service> services = proto.getServices();
        for (Service type : services) {
            type.setProto(proto);
            String fullyQualifiedName = proto.getNamespace() + type.getName();
            type.setFullyQualifiedName(fullyQualifiedName);
            context.register(fullyQualifiedName, type);
        }

        for (Message message : messages) {
            registerNestedUserTypes(context, message);
        }

    }

    private void registerNestedUserTypes(final ProtoContext context, final UserTypeContainer parent) {
        List<Message> nestedMessages = new ArrayList<Message>();
        nestedMessages.addAll(parent.getMessages());
        if (parent instanceof GroupContainer) {
            nestedMessages.addAll(((GroupContainer) parent).getGroups());
        }
        for (Extension extension : parent.getDeclaredExtensions()) {
            nestedMessages.addAll(extension.getGroups());
        }

        List<Enum> nestedEnums = parent.getEnums();
        Visitor<UserType> nestedTypeProcessor = new Visitor<UserType>() {
            @Override
            public void visit(UserType type) {
                type.setProto(context.getProto());
                String fullyQualifiedName = parent.getNamespace() + type.getName();
                type.setFullyQualifiedName(fullyQualifiedName);
                context.register(fullyQualifiedName, type);
            }
        };
        Visitors.run(nestedEnums, nestedTypeProcessor);
        Visitors.run(nestedMessages, nestedTypeProcessor);
        Visitors.run(nestedMessages, new Visitor<Message>() {
            @Override
            public void visit(Message message) {
                registerNestedUserTypes(context, message);
            }
        });
    }
}
