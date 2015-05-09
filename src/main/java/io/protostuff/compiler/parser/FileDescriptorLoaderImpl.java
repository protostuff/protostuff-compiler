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
    private final Importer importer;

    @Inject
    public FileDescriptorLoaderImpl(Importer importer, ANTLRErrorListener errorListener) {
        this.errorListener = errorListener;
        this.importer = importer;
    }

    @Override
    public ProtoContext load(String name, CharStream stream) {
        ProtoContext context = parse(name, stream);
        if (context == null) {
            return null;
        }

        for (String anImport : context.getProto().getPublicImports()) {
            ProtoContext importedContext = importer.importFile(anImport);
            context.addPublicImport(importedContext);
        }

        for (String anImport : context.getProto().getImports()) {
            ProtoContext importedContext = importer.importFile(anImport);
            context.addImport(importedContext);
        }

        registerUserTypes(context);

        resolveTypeReferences(context);

        registerExtensions(context, context.getProto());

        context.setInitialized(true);
        return context;
    }

    private void registerExtensions(ProtoContext context, UserTypeContainer container) {
        List<Extension> extensions = container.getExtensions();
        for (Extension extension : extensions) {
            context.registerExtension(extension);
        }
        for (Message message : container.getMessages()) {
            registerExtensions(context, message);
        }
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

        for (Service service : proto.getServices()) {

            for (ServiceMethod method : service.getMethods()) {
                String argTypeName = method.getArgTypeName();
                FieldType argType = resolveFieldType(context, scopeLookupList, argTypeName);
                if (!(argType instanceof Message)) {
                    throw new ParserException("Can not use %s as a service argument: not a message", argType.getReference());
                }
                method.setArgType((Message) argType);

                String returnTypeName = method.getReturnTypeName();
                FieldType returnType = resolveFieldType(context, scopeLookupList, returnTypeName);
                if (!(returnType instanceof Message)) {
                    throw new ParserException("Can not use %s as a service return value: not a message", returnType.getReference());
                }
                method.setReturnType((Message) returnType);
            }
        }

        resolveTypeReferences(context, scopeLookupList, proto);

    }

    private void resolveTypeReferences(ProtoContext context, Deque<String> scopeLookupList, UserTypeContainer container) {
        for (Extension extension : container.getExtensions()) {
            String extendeeName = extension.getExtendeeName();
            UserFieldType type = resolveUserType(context, scopeLookupList, extendeeName);
            if (!(type instanceof Message)) {
                throw new ParserException("Can not extend %s: not a message", type.getFullName());
            }
            Message extendee = (Message) type;
            extension.setExtendee(extendee);

            for (Field field : extension.getFields()) {
                String typeName = field.getTypeName();
                FieldType fieldType = resolveFieldType(context, scopeLookupList, typeName);
                field.setType(fieldType);
            }
        }

        for (Message message : container.getMessages()) {
            String root = scopeLookupList.peek();
            scopeLookupList.push(root + message.getName() + ".");
            for (Field field : message.getFields()) {
                String typeName = field.getTypeName();
                FieldType fieldType = resolveFieldType(context, scopeLookupList, typeName);
                field.setType(fieldType);
            }
            resolveTypeReferences(context, scopeLookupList, message);
            scopeLookupList.pop();
        }
    }

    private FieldType resolveFieldType(ProtoContext context, Deque<String> scopeLookupList, String typeName) {
        ScalarFieldType scalarFieldType = ScalarFieldType.getByName(typeName);
        if (scalarFieldType != null) {
            return scalarFieldType;
        } else {
            return resolveUserType(context, scopeLookupList, typeName);
        }
    }

    private UserFieldType resolveUserType(ProtoContext context, Deque<String> scopeLookupList, String typeName) {
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
            throw new ParserException(format, typeName);
        }
        return fieldType;
    }

    private void registerUserTypes(ProtoContext context) {
        final Proto proto = context.getProto();
        List<Message> messages = proto.getMessages();
        for (Message type : messages) {
            type.setProto(proto);
            type.setParent(proto);
            type.setNested(false);
            String fullName = proto.getNamespacePrefix() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        }

        List<Enum> enums = proto.getEnums();
        for (Enum type : enums) {
            type.setProto(proto);
            type.setParent(proto);
            type.setNested(false);
            String fullName = proto.getNamespacePrefix() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        }

        List<Service> services = proto.getServices();
        for (Service type : services) {
            type.setProto(proto);
            String fullName = proto.getNamespacePrefix() + type.getName();
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
                new OptionParseListener(context),
                new ServiceParseListener(context)
        );
        ParseTreeWalker.DEFAULT.walk(composite, tree);
        if (parser.getNumberOfSyntaxErrors() > 0) {
            return null;
        }

        return context;
    }

}
