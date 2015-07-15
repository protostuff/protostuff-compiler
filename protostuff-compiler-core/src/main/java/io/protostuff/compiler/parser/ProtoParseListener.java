package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Import;
import io.protostuff.compiler.model.Package;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Syntax;
import org.antlr.v4.runtime.BufferedTokenStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoParseListener extends AbstractProtoParserListener {

    protected ProtoParseListener(BufferedTokenStream tokens, ProtoContext context) {
        super(tokens, context);
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
        String text = ctx.STRING_VALUE().getText();
        String fileName = Util.removeFirstAndLastChar(text);
        Import anImport = new Import(proto, fileName, ctx.PUBLIC() != null);
        anImport.setSourceCodeLocation(getSourceCodeLocation(ctx));
        proto.addImport(anImport);
    }
}
