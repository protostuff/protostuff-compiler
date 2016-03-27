package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.BufferedTokenStream;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.EnumConstant;
import io.protostuff.compiler.model.EnumContainer;
import io.protostuff.compiler.model.UserTypeContainer;

/**
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
        String name = ctx.name().getText();
        e.setName(name);
        e.setSourceCodeLocation(getSourceCodeLocation(ctx));
        container.addEnum(e);
        attachComments(ctx, e, false);
    }

    @Override
    public void enterEnumConstant(ProtoParser.EnumConstantContext ctx) {
        Enum parent = context.peek(Enum.class);
        EnumConstant enumConstant = new EnumConstant(parent);
        context.push(enumConstant);
    }

    @Override
    public void exitEnumConstant(ProtoParser.EnumConstantContext ctx) {
        EnumConstant enumConstant = context.pop(EnumConstant.class);
        Enum e = context.peek(Enum.class);
        String name = ctx.name().getText();
        int number = Integer.decode(ctx.INTEGER_VALUE().getText());
        enumConstant.setName(name);
        enumConstant.setValue(number);
        enumConstant.setSourceCodeLocation(getSourceCodeLocation(ctx));
        e.addConstant(enumConstant);
        attachComments(ctx, enumConstant, true);
    }

}
