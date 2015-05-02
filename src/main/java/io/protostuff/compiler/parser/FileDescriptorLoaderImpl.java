package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.*;
import io.protostuff.compiler.model.Enum;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.inject.Inject;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class FileDescriptorLoaderImpl implements FileDescriptorLoader {

    private final ANTLRErrorListener errorListener;

    @Inject
    public FileDescriptorLoaderImpl(ANTLRErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    @Override
    public ProtoContext load(String name, CharStream stream) {
        ProtoContext context = parse(name, stream);
        if (context == null) {
            return null;
        }

        registerUserTypes(context);

        resolveTypeReferences(context);

        return context;
    }

    private void resolveTypeReferences(ProtoContext context) {
        Deque<String> scopeLookupList = new ArrayDeque<>();
        String root = ".";
        scopeLookupList.add(root);
        Proto proto = context.getProto();
        String packageName = proto.getPackageName();
        if (packageName != null) {
            String[] split = packageName.split("\\.");
            for (String s : split) {
                String nextRoot = root + s + ".";
                scopeLookupList.push(nextRoot);
                root = nextRoot;
            }
        }
        resolveTypeReferences(context, scopeLookupList, proto);

    }

    private void resolveTypeReferences(ProtoContext context, Deque<String> scopeLookupList, UserTypeContainer container) {
        for (Extension extension : container.getExtensions()) {
            String extendeeName = extension.getExtendeeName();
            UserType type = resolveUserTypeReference(context, scopeLookupList, extendeeName);
            if (!(type instanceof Message)) {
                throw new ParserException("Can not extend %s: not a message", type.getFullName());
            }
            Message extendee = (Message) type;
            extension.setExtendee(extendee);

            String typeName = extension.getTypeName();
            FieldType fieldType = resolveTypeReference(context, scopeLookupList, typeName);
            extension.setType(fieldType);
        }

        for (Message message : container.getMessages()) {
            String root = scopeLookupList.peek();
            scopeLookupList.push(root + message.getName() + ".");
            for (Field field : message.getFields()) {
                String typeName = field.getTypeName();
                FieldType fieldType = resolveTypeReference(context, scopeLookupList, typeName);
                field.setType(fieldType);
            }
            resolveTypeReferences(context, scopeLookupList, message);
            scopeLookupList.pop();
        }
    }

    private FieldType resolveTypeReference(ProtoContext context, Deque<String> scopeLookupList, String typeName) {
        ScalarFieldType scalarFieldType = ScalarFieldType.getByName(typeName);
        if (scalarFieldType != null) {
            return scalarFieldType;
        } else {
            return resolveUserTypeReference(context, scopeLookupList, typeName);
        }
    }

    private UserType resolveUserTypeReference(ProtoContext context, Deque<String> scopeLookupList, String typeName) {
        UserType fieldType = null;
        if (typeName.startsWith(".")) {
            UserType type = context.resolve(typeName);
            if (type != null) {
                fieldType = type;
            }
        } else {
            for (String scope : scopeLookupList) {
                String fullTypeName = scope + typeName;
                UserType type = context.resolve(fullTypeName);
                if (type != null) {
                    fieldType = type;
                    break;
                }
            }
        }
        if (fieldType == null) {
            String format = "Unresolved reference: '%s'";
            throw new ParserException(format, typeName);
        }
        return fieldType;
    }

    private void registerUserTypes(ProtoContext context) {
        final Proto proto = context.getProto();
        List<Message> messages = proto.getMessages();
        List<Enum> enums = proto.getEnums();
        Consumer<UserType> registerUserTypes = type -> {
            type.setProto(proto);
            type.setParent(proto);
            type.setNested(false);
            String fullName = proto.getNamespacePrefix() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        };
        messages.forEach(registerUserTypes);
        enums.forEach(registerUserTypes);
        messages.forEach(message -> registerNestedUserTypes(context, message));
    }

    private void registerNestedUserTypes(ProtoContext context, UserTypeContainer parent) {
        List<Message> nestedMessages = parent.getMessages();
        List<Enum> nestedEnums = parent.getEnums();
        Consumer<UserType> nestedTypeProcessor = type -> {
            type.setProto(context.getProto());
            type.setParent(parent);
            type.setNested(true);
            String fullName = parent.getNamespacePrefix() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        };
        nestedEnums.forEach(nestedTypeProcessor);
        nestedMessages.forEach(nestedTypeProcessor);
        nestedMessages.forEach(message -> registerNestedUserTypes(context, message));
    }

    private ProtoContext parse(String name, CharStream stream) {
        ProtoLexer lexer = new ProtoLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ProtoParser parser = new ProtoParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        ProtoParser.ProtoContext tree = parser.proto();
        ProtoContext context = new ProtoContext(name);
        ProtoParserListener composite = CompositeParseTreeListener.create(ProtoParserListener.class,
                new ProtoParseListener(context),
                new MessageParseListener(context),
                new EnumParseListener(context),
                new OptionParseListener(context)
        );
        ParseTreeWalker.DEFAULT.walk(composite, tree);
        if (parser.getNumberOfSyntaxErrors() > 0) {
            return null;
        }

        return context;
    }

}
