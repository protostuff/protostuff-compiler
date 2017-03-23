package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Import;
import io.protostuff.compiler.model.Package;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Syntax;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoParseListener extends AbstractProtoParserListener {

    private final BufferedTokenStream tokens;

    ProtoParseListener(BufferedTokenStream tokens, ProtoContext context) {
        super(tokens, context);
        this.tokens = tokens;
    }

    @Override
    public void exitProto(ProtoParser.ProtoContext ctx) {
        int i = 0;
        List<String> comments = new ArrayList<>();
        while (i < tokens.size() && isWhitespace(tokens.get(i))) {
            // skip whitespaces until we reach line comments
            i++;
        }
        while (i < tokens.size() && isComment(tokens.get(i))) {
            // consume all consecutive line comments
            Token token = tokens.get(i);
            // skip processed LINE_COMMENT and following NL
            i += 2;
            String text = getTextFromLineCommentToken(token);
            comments.add(text);
        }
        if (i < tokens.size()) {
            // check if next token is not element that is owner of our comment block
            Token token = tokens.get(i);
            if (isCommentBlockOwner(token)) {
                return;
            }
        }
        List<String> trimComments = trim(comments);
        for (String comment : trimComments) {
            context.getProto().addComment(comment);
        }
    }

    private boolean isComment(Token token) {
        return token.getChannel() == ProtoLexer.HIDDEN
                && token.getType() == ProtoLexer.LINE_COMMENT;
    }

    private boolean isWhitespace(Token token) {
        return token.getChannel() == ProtoLexer.HIDDEN
                && token.getType() != ProtoLexer.LINE_COMMENT;
    }

    private boolean isCommentBlockOwner(Token token) {
        int type = token.getType();
        return type == ProtoLexer.MESSAGE
                || type == ProtoLexer.ENUM
                || type == ProtoLexer.SERVICE;
    }

    @Override
    public void exitSyntax(ProtoParser.SyntaxContext ctx) {
        Proto proto = context.peek(Proto.class);
        String text = ctx.STRING_VALUE().getText();
        String value = Util.removeFirstAndLastChar(text);
        Syntax syntax = new Syntax(proto, value);
        syntax.setSourceCodeLocation(getSourceCodeLocation(ctx));
        proto.setSyntax(syntax);
    }

    @Override
    public void exitPackageStatement(ProtoParser.PackageStatementContext ctx) {
        Proto proto = context.peek(Proto.class);
        String packageName = ctx.packageName().getText();
        Package aPackage = new Package(proto, packageName);
        aPackage.setSourceCodeLocation(getSourceCodeLocation(ctx));
        proto.setPackage(aPackage);
    }

    @Override
    public void exitImportStatement(ProtoParser.ImportStatementContext ctx) {
        Proto proto = context.peek(Proto.class);
        String text = ctx.fileReference().getText();
        String fileName = Util.removeFirstAndLastChar(text);
        Import anImport = new Import(proto, fileName, ctx.PUBLIC() != null);
        anImport.setSourceCodeLocation(getSourceCodeLocation(ctx));
        proto.addImport(anImport);
    }
}
