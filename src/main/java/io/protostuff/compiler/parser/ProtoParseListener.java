package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Proto;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoParseListener extends ProtoParserBaseListener {

    private final ProtoContext context;

    public ProtoParseListener(ProtoContext context) {
        this.context = context;
    }

    @Override
    public void exitSyntax(ProtoParser.SyntaxContext ctx) {
        Proto proto = context.peek(Proto.class);
        String text = ctx.STRING_VALUE().getText();
        String syntax = Util.removeQuotes(text);
        proto.setSyntax(syntax);
    }

    @Override
    public void exitPackageStatement(ProtoParser.PackageStatementContext ctx) {
        Proto proto = context.peek(Proto.class);
        String packageName = ctx.packageName().getText();
        proto.setPackageName(packageName);
    }

    @Override
    public void exitImportStatement(ProtoParser.ImportStatementContext ctx) {
        Proto proto = context.peek(Proto.class);
        String text = ctx.STRING_VALUE().getText();
        String fileName = Util.removeQuotes(text);
        if (ctx.PUBLIC() == null) {
            proto.addImport(fileName);
        } else {
            proto.addPublicImport(fileName);
        }
    }
}
