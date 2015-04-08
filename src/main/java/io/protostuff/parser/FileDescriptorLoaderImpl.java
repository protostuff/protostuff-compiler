package io.protostuff.parser;

import io.protostuff.model.Enum;
import io.protostuff.model.*;
import io.protostuff.parser.api.FileDescriptorLoader;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.inject.Inject;

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
        walk(proto, (container, type) -> {
            type.setProto(proto);
            String prefix = container.getNamespacePrefix();
            String fullName = prefix + type.getName();
            type.setFullName(fullName);
            context.register(fullName, type);
        });
        return context;
    }

    private void walk(UserTypeContainer container, Operation operation) {
        for (Message message : container.getMessages()) {
            operation.process(container, message);
        }
        for (Enum anEnum : container.getEnums()) {
            operation.process(container, anEnum);
        }
        for (Message message : container.getMessages()) {
            walk(message, operation);
        }
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
        ParseTreeWalker walker = new ParseTreeWalker();
        ProtoContext context = new ProtoContext(name);
        Proto3Listener composite = CompositeParseTreeListener.create(Proto3Listener.class,
                new ProtoParseListener(context),
                new MessageParseListener(context),
                new EnumParseListener(context),
                new OptionParseListener(context)
        );
        walker.walk(composite, tree);
        if (parser.getNumberOfSyntaxErrors() > 0) {
            return null;
        }

        return context;
    }

    interface Operation {
        void process(UserTypeContainer container, UserType type);
    }

}
