package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.EnumConstant;
import io.protostuff.compiler.model.EnumContainer;
import io.protostuff.compiler.model.UserTypeContainer;
import org.antlr.v4.runtime.BufferedTokenStream;

/**
 * Enum parse listener, responsible for processing enums.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumParseListener extends AbstractProtoParserListener {

    protected EnumParseListener(BufferedTokenStream tokens, ProtoContext context) {
        super(tokens, context);
    }

    @Override
    public void enterEnumBlock(ProtoParser.EnumBlockContext ctx) {
        UserTypeContainer parent = context.peek(UserTypeContainer.class);
        Enum enumBuilder = new Enum(parent);
        context.push(enumBuilder);
    }

    @Override
    public void exitEnumBlock(ProtoParser.EnumBlockContext ctx) {
        Enum e = context.pop(Enum.class);
        EnumContainer container = context.peek(EnumContainer.class);
        String name = ctx.enumName().getText();
        e.setName(name);
        e.setSourceCodeLocation(getSourceCodeLocation(ctx));
        container.addEnum(e);
        attachComments(ctx, e, false);
    }

    @Override
    public void enterEnumField(ProtoParser.EnumFieldContext ctx) {
        Enum parent = context.peek(Enum.class);
        EnumConstant enumConstant = new EnumConstant(parent);
        context.push(enumConstant);
    }

    @Override
    public void exitEnumField(ProtoParser.EnumFieldContext ctx) {
        EnumConstant enumConstant = context.pop(EnumConstant.class);
        final Enum e = context.peek(Enum.class);
        String name = ctx.enumFieldName().getText();
        int number = Integer.decode(ctx.enumFieldValue().getText());
        enumConstant.setName(name);
        enumConstant.setValue(number);
        enumConstant.setSourceCodeLocation(getSourceCodeLocation(ctx));
        e.addConstant(enumConstant);
        attachComments(ctx, enumConstant, true);
    }

}
