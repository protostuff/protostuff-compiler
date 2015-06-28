package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.*;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class TypeResolverPostProcessor implements ProtoContextPostProcessor {

    @Override
    public void process(ProtoContext context) {
            resolveTypeReferences(context);
    }

    private void resolveTypeReferences(ProtoContext context) {
        Deque<String> scopeLookupList = new ArrayDeque<>();
        String root = ".";
        scopeLookupList.add(root);
        Proto proto = context.getProto();
        io.protostuff.compiler.model.Package aPackage = proto.getPackage();
        if (aPackage != null) {
            String[] split = aPackage.getValue().split("\\.");
            for (String s : split) {
                String nextRoot = root + s + ".";
                scopeLookupList.push(nextRoot);
                root = nextRoot;
            }
        }

        for (Service service : proto.getServices()) {

            for (ServiceMethod method : service.getMethods()) {
                String argTypeName = method.getArgTypeName();
                FieldType argType = resolveFieldType(method, context, scopeLookupList, argTypeName);
                if (!(argType instanceof Message)) {
                    String format = "Cannot use '%s' as a service method argument type: not a message";
                    throw new ParserException(method, format, argType.getName());
                }
                method.setArgType((Message) argType);

                String returnTypeName = method.getReturnTypeName();
                FieldType returnType = resolveFieldType(method, context, scopeLookupList, returnTypeName);
                if (!(returnType instanceof Message)) {
                    String format = "Cannot use '%s' as a service method return type: not a message";
                    throw new ParserException(method, format, returnType.getName());
                }
                method.setReturnType((Message) returnType);
            }
        }

        resolveTypeReferences(context, scopeLookupList, proto);

    }

    private void resolveTypeReferences(ProtoContext context, Deque<String> scopeLookupList, UserTypeContainer container) {
        ExtensionRegistry extensionRegistry = context.getExtensionRegistry();
        for (Extension extension : container.getDeclaredExtensions()) {
            String extendeeName = extension.getExtendeeName();
            UserFieldType type = resolveUserType(extension, context, scopeLookupList, extendeeName);
            if (!(type instanceof Message)) {
                throw new ParserException(extension, "Cannot extend '%s': not a message", type.getName());
            }
            Message extendee = (Message) type;
            extension.setExtendee(extendee);
            for (Field field : extension.getFields()) {
                String typeName = field.getTypeName();
                FieldType fieldType = resolveFieldType(field, context, scopeLookupList, typeName);
                field.setType(fieldType);
            }
        }

        for (Message message : container.getMessages()) {
            String root = scopeLookupList.peek();
            scopeLookupList.push(root + message.getName() + ".");
            updateFieldTypes(context, scopeLookupList, message);
            for (Oneof oneof : message.getOneofs()) {
                updateFieldTypes(context, scopeLookupList, oneof);
            }
            resolveTypeReferences(context, scopeLookupList, message);
            scopeLookupList.pop();
        }
    }

    private void updateFieldTypes(ProtoContext context, Deque<String> scopeLookupList, FieldContainer fieldContainer) {
        for (Field field : fieldContainer.getFields()) {
            String typeName = field.getTypeName();
            FieldType fieldType = resolveFieldType(field, context, scopeLookupList, typeName);
            field.setType(fieldType);
        }
    }

    private FieldType resolveFieldType(Element source, ProtoContext context, Deque<String> scopeLookupList, String typeName) {
        ScalarFieldType scalarFieldType = ScalarFieldType.getByName(typeName);
        if (scalarFieldType != null) {
            return scalarFieldType;
        } else {
            return resolveUserType(source, context, scopeLookupList, typeName);
        }
    }

    private UserFieldType resolveUserType(Element source, ProtoContext context, Deque<String> scopeLookupList, String typeName) {
        UserFieldType fieldType = null;
        if (typeName.startsWith(".")) {
            UserFieldType type = (UserFieldType)context.resolve(typeName);
            if (type != null) {
                fieldType = type;
            }
        } else {
            for (String scope : scopeLookupList) {
                String fullTypeName = scope + typeName;
                UserFieldType type = (UserFieldType)context.resolve(fullTypeName);
                if (type != null) {
                    fieldType = type;
                    break;
                }
            }
        }
        if (fieldType == null) {
            String format = "Unresolved reference: '%s'";
            throw new ParserException(source, format, typeName);
        }
        return fieldType;
    }

}
