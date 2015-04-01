package io.protostuff.parser;

import io.protostuff.parser.api.FileDescriptorLoader;
import io.protostuff.proto3.FileDescriptor;
import org.antlr.v4.runtime.*;

import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class FileDescriptorLoaderImpl implements FileDescriptorLoader {

    private final ANTLRErrorListener errorListener;
    private final ANTLRErrorStrategy errorStrategy;

    @Inject
    public FileDescriptorLoaderImpl(ANTLRErrorListener errorListener, ANTLRErrorStrategy errorStrategy) {
        this.errorListener = errorListener;
        this.errorStrategy = errorStrategy;
    }

    @Override
    public FileDescriptor parse(String name, CharStream stream) {
        Proto3Lexer lexer = new Proto3Lexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Proto3Parser parser = new Proto3Parser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
//        parser.setErrorHandler(errorStrategy);

        Context context = new Context();
        parser.addParseListener(new ProtoParseListener(context));
        parser.addParseListener(new MessageParseListener(context));
        parser.addParseListener(new EnumParseListener(context));
        parser.addParseListener(new OptionParseListener(context));

        Proto3Parser.ProtoContext tree = parser.proto();
//        ParseTreeWalker walker = new ParseTreeWalker();
//        Context context = new Context();
//        Proto3Listener composite = CompositeParseTreeListenerFactory.create(Proto3Listener.class,
//                new ProtoParseListener(context),
//                new MessageParseListener(context),
//                new EnumParseListener(context),
//                new OptionParseListener(context)
//        );
//        walker.walk(composite, tree);
        if (parser.getNumberOfSyntaxErrors() > 0) {

            return null;
        }
        return context.getResult();
    }

}
