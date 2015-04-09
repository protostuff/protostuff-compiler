package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Proto;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoParseListener extends Proto3BaseListener {

    private final ProtoContext context;

    public ProtoParseListener(ProtoContext context) {
        this.context = context;
    }

    @Override
    public void exitSyntax(Proto3Parser.SyntaxContext ctx) {
        Proto proto = context.peek(Proto.class);
        String text = ctx.STRING_VALUE().getText();
        String syntax = Util.removeQuotes(text);
        proto.setSyntax(syntax);
    }

    @Override
    public void exitPackageStatement(Proto3Parser.PackageStatementContext ctx) {
        Proto proto = context.peek(Proto.class);
        String packageName = ctx.declarationRef().getText();
        proto.setPackageName(packageName);
    }

    @Override
    public void exitImportStatement(Proto3Parser.ImportStatementContext ctx) {
        Proto proto = context.peek(Proto.class);
        String text = ctx.STRING_VALUE().getText();
        String fileName = Util.removeQuotes(text);
        proto.addImport(fileName);
    }
}
