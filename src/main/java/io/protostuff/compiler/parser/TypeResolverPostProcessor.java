package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Element;
import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.FieldContainer;
import io.protostuff.compiler.model.FieldType;
import io.protostuff.compiler.model.Map;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Oneof;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.ScalarFieldType;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import io.protostuff.compiler.model.UserType;
import io.protostuff.compiler.model.UserTypeContainer;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class TypeResolverPostProcessor implements ProtoContextPostProcessor {

    public static final String ILLEGAL_KEY_TYPE = "Illegal key type: %s";

    @Override
    public void process(ProtoContext context) {
        resolveTypeReferences(context);
    }

    private void resolveTypeReferences(ProtoContext context) {
        Proto proto = context.getProto();
        Deque<String> scopeLookupList = createScopeLookupList(proto);

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

    // Type name resolution in the protocol buffer language works like C++: first
    // the innermost scope is searched, then the next-innermost, and so on, with
    // each package considered to be "inner" to its parent package.
    public static Deque<String> createScopeLookupList(UserTypeContainer container) {
        String namespace = container.getNamespace();
        Deque<String> scopeLookupList = new ArrayDeque<>();
        int end = 0;
        while (end >= 0) {
            end = namespace.indexOf('.', end);
            if (end >= 0) {
                end++;
                String scope = namespace.substring(0, end);
                scopeLookupList.addFirst(scope);
            }
        }
        return scopeLookupList;
    }

    private void resolveTypeReferences(ProtoContext context, Deque<String> scopeLookupList, UserTypeContainer container) {
        for (Extension extension : container.getDeclaredExtensions()) {
            String extendeeName = extension.getExtendeeName();
            UserType type = resolveUserType(extension, context, scopeLookupList, extendeeName);
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
            for (Map map : message.getMaps()) {
                String keyTypeName = map.getKeyTypeName();
                FieldType keyType = resolveFieldType(map, context, scopeLookupList, keyTypeName);
                if (keyType instanceof ScalarFieldType) {
                    ScalarFieldType scalarFieldType = (ScalarFieldType) keyType;
                    if (scalarFieldType == ScalarFieldType.FLOAT
                            || scalarFieldType == ScalarFieldType.DOUBLE
                            || scalarFieldType == ScalarFieldType.BYTES) {
                        throw new ParserException(map, ILLEGAL_KEY_TYPE, keyType.getName());
                    }
                    map.setKeyType(scalarFieldType);
                } else {
                    throw new ParserException(map, ILLEGAL_KEY_TYPE, keyType.getName());
                }

                String valueTypeName = map.getValueTypeName();
                FieldType valueType = resolveFieldType(map, context, scopeLookupList, valueTypeName);
                map.setValueType(valueType);
            }

            resolveTypeReferences(context, scopeLookupList, message);
            scopeLookupList.pop();
        }
    }

    private void updateFieldTypes(ProtoContext context, Deque<String> scopeLookupList, FieldContainer fieldContainer) {
        // check if field type isn't already set
        fieldContainer.getFields()
                .stream()
                .filter(field -> field.getType() == null) // for map fields it is set by parser
                .forEach(field -> {
                    String typeName = field.getTypeName();
                    FieldType fieldType = resolveFieldType(field, context, scopeLookupList, typeName);
                    field.setType(fieldType);
                });
    }

    private FieldType resolveFieldType(Element source, ProtoContext context, Deque<String> scopeLookupList, String typeName) {
        ScalarFieldType scalarFieldType = ScalarFieldType.getByName(typeName);
        if (scalarFieldType != null) {
            return scalarFieldType;
        } else {
            return resolveUserType(source, context, scopeLookupList, typeName);
        }
    }

    private UserType resolveUserType(Element source, ProtoContext context, Deque<String> scopeLookupList, String typeName) {
        UserType fieldType = null;
        // A leading '.' (for example, .foo.bar.Baz) means to start from the outermost scope
        if (typeName.startsWith(".")) {
            UserType type = (UserType) context.resolve(typeName);
            if (type != null) {
                fieldType = type;
            }
        } else {
            for (String scope : scopeLookupList) {
                String fullTypeName = scope + typeName;
                UserType type = (UserType) context.resolve(fullTypeName);
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
