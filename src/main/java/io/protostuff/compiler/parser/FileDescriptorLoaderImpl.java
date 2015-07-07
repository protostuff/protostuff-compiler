package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class FileDescriptorLoaderImpl implements FileDescriptorLoader {

    private final ANTLRErrorListener errorListener;

    private final Set<ProtoContextPostProcessor> postProcessors;

    @Inject
    public FileDescriptorLoaderImpl(ANTLRErrorListener errorListener, Set<ProtoContextPostProcessor> postProcessors) {
        this.errorListener = errorListener;
        this.postProcessors = postProcessors;
    }

    @Override
    public ProtoContext load(String name, CharStream stream) {
        ProtoContext context = parse(name, stream);
        postProcessors.forEach(p -> p.process(context));
        context.setInitialized(true);
        return context;
    }

    private ProtoContext parse(String filename, CharStream stream) {
        ProtoLexer lexer = new ProtoLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ProtoParser parser = new ProtoParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        ProtoParser.ProtoContext tree = parser.proto();
        int numberOfSyntaxErrors = parser.getNumberOfSyntaxErrors();
        if (numberOfSyntaxErrors > 0) {
            String format = "Could not parse %s: %d syntax errors found";
            throw new ParserException(format, filename, numberOfSyntaxErrors);
        }
        ProtoContext context = new ProtoContext(filename);
        ProtoParserListener composite = CompositeParseTreeListener.create(ProtoParserListener.class,
                new ProtoParseListener(tokenStream, context),
                new MessageParseListener(tokenStream, context),
                new EnumParseListener(tokenStream, context),
                new OptionParseListener(tokenStream, context),
                new ServiceParseListener(tokenStream, context)
        );
        ParseTreeWalker.DEFAULT.walk(composite, tree);
        return context;
    }

}
