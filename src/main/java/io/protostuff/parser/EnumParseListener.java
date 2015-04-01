package io.protostuff.parser;

import io.protostuff.proto3.*;
import io.protostuff.proto3.Enum;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumParseListener extends Proto3BaseListener {

    private final Context context;

//    private EnumDesPro
    public EnumParseListener(Context context) {
        this.context = context;
    }

    @Override
    public void enterEnumBlock(Proto3Parser.EnumBlockContext ctx) {
        Enum enumBuilder = new Enum();
        context.push(enumBuilder);
    }

    @Override
    public void exitEnumBlock(Proto3Parser.EnumBlockContext ctx) {
        Enum e = context.pop(Enum.class);
        AbstractUserTypeContainer container = context.peek(AbstractUserTypeContainer.class);
        String name = ctx.NAME().getText();
        e.setName(name);
        container.addEnum(e);
    }

    @Override
    public void enterEnumConstant(Proto3Parser.EnumConstantContext ctx) {
        EnumConstant enumConstant = new EnumConstant();
        context.push(enumConstant);
    }

    @Override
    public void exitEnumConstant(Proto3Parser.EnumConstantContext ctx) {
        EnumConstant enumConstant = context.pop(EnumConstant.class);
        Enum e = context.peek(Enum.class);
        String name = ctx.NAME().getText();
        int number = Integer.decode(ctx.INTEGER_VALUE().getText());
        enumConstant.setName(name);
        enumConstant.setValue(number);
        e.addValue(enumConstant);
    }

}
