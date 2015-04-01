package io.protostuff.parser;

import com.google.protobuf.DescriptorProtos;
import io.protostuff.proto3.FileDescriptor;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoParseListener extends Proto3BaseListener {

    private final Context context;

    public ProtoParseListener(Context context) {
        this.context = context;
    }

    @Override
    public void enterProto(Proto3Parser.ProtoContext ctx) {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setName("not implemented"); // TODO
        context.push(fileDescriptor);
    }

    @Override
    public void exitProto(Proto3Parser.ProtoContext ctx) {
        FileDescriptor fileDescriptor = context.pop(FileDescriptor.class);
        context.setResult(fileDescriptor);
    }

    @Override
    public void exitSyntax(Proto3Parser.SyntaxContext ctx) {
        FileDescriptor fileDescriptor = context.peek(FileDescriptor.class);
        String text = ctx.STRING_VALUE().getText();
        String syntax = Util.removeQuotes(text);
        fileDescriptor.setSyntax(syntax);
    }

    @Override
    public void exitPackageStatement(Proto3Parser.PackageStatementContext ctx) {
        FileDescriptor fileDescriptor = context.peek(FileDescriptor.class);
        String packageName = ctx.declarationRef().getText();
        fileDescriptor.setPackageName(packageName);
    }

    @Override
    public void exitImportStatement(Proto3Parser.ImportStatementContext ctx) {
        FileDescriptor fileDescriptor = context.peek(FileDescriptor.class);
        String text = ctx.STRING_VALUE().getText();
        String fileName = Util.removeQuotes(text);
        fileDescriptor.addImport(fileName);
    }
}
