package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.*;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.util.ProtoTreeWalker;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.inject.Inject;
import java.util.List;
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
        final Proto proto = context.getProto();

        List<Message> messages = proto.getMessages();
        List<io.protostuff.compiler.model.Enum> enums = proto.getEnums();

        Consumer<UserType> rootTypeProcessor = type -> {
            type.setProto(proto);
            type.setParent(proto);
            type.setNested(false);
            String fullName = proto.getNamespacePrefix() + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        };

        messages.forEach(rootTypeProcessor);
        enums.forEach(rootTypeProcessor);

        messages.forEach(message -> rec(context, message));

        return context;
    }

    private void rec(ProtoContext context, UserTypeContainer parent) {
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
        nestedMessages.forEach(message -> rec(context, message));
    }

    private ProtoContext parse(String name, CharStream stream) {
        Proto3Lexer lexer = new Proto3Lexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Proto3Parser parser = new Proto3Parser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        Proto3Parser.ProtoContext tree = parser.proto();
        ProtoContext context = new ProtoContext(name);
        Proto3Listener composite = CompositeParseTreeListener.create(Proto3Listener.class,
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
