package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.*;
import io.protostuff.compiler.model.Package;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoParseListener extends AbstractProtoParsetListener {

    public ProtoParseListener(ProtoContext context) {
        super(context);
    }

    @Override
    public void exitSyntax(ProtoParser.SyntaxContext ctx) {
        Proto proto = context.peek(Proto.class);
        String text = ctx.STRING_VALUE().getText();
        String value = Util.removeFirstAndLastChar(text);
        Syntax syntax = new Syntax(value);
        syntax.setSourceCodeLocation(getSourceCodeLocation(ctx));
        proto.setSyntax(syntax);
    }

    @Override
    public void exitPackageStatement(ProtoParser.PackageStatementContext ctx) {
        Proto proto = context.peek(Proto.class);
        String packageName = ctx.packageName().getText();
        Package aPackage = new Package(packageName);
        aPackage.setSourceCodeLocation(getSourceCodeLocation(ctx));
        proto.setPackage(aPackage);
    }

    @Override
    public void exitImportStatement(ProtoParser.ImportStatementContext ctx) {
        Proto proto = context.peek(Proto.class);
        String text = ctx.STRING_VALUE().getText();
        String fileName = Util.removeFirstAndLastChar(text);
        Import anImport = new Import(fileName, ctx.PUBLIC() != null);
        anImport.setSourceCodeLocation(getSourceCodeLocation(ctx));
        proto.addImport(anImport);
    }
}
