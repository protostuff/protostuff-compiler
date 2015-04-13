package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.AbstractUserTypeContainer;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.EnumConstant;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumParseListener extends ProtoParserBaseListener {

    private final ProtoContext context;

    //    private EnumDesPro
    public EnumParseListener(ProtoContext context) {
        this.context = context;
    }

    @Override
    public void enterEnumBlock(ProtoParser.EnumBlockContext ctx) {
        Enum enumBuilder = new Enum();
        context.push(enumBuilder);
    }

    @Override
    public void exitEnumBlock(ProtoParser.EnumBlockContext ctx) {
        Enum e = context.pop(Enum.class);
        AbstractUserTypeContainer container = context.peek(AbstractUserTypeContainer.class);
        String name = ctx.NAME().getText();
        e.setName(name);
        container.addEnum(e);
    }

    @Override
    public void enterEnumConstant(ProtoParser.EnumConstantContext ctx) {
        EnumConstant enumConstant = new EnumConstant();
        context.push(enumConstant);
    }

    @Override
    public void exitEnumConstant(ProtoParser.EnumConstantContext ctx) {
        EnumConstant enumConstant = context.pop(EnumConstant.class);
        Enum e = context.peek(Enum.class);
        String name = ctx.NAME().getText();
        int number = Integer.decode(ctx.INTEGER_VALUE().getText());
        enumConstant.setName(name);
        enumConstant.setValue(number);
        e.addValue(enumConstant);
    }

}
