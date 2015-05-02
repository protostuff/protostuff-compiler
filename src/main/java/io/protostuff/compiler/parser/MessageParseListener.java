package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.*;

import static io.protostuff.compiler.model.FieldModifier.OPTIONAL;
import static io.protostuff.compiler.model.FieldModifier.REPEATED;
import static io.protostuff.compiler.model.FieldModifier.REQUIRED;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageParseListener extends ProtoParserBaseListener {

    private final ProtoContext context;

    public MessageParseListener(ProtoContext context) {
        this.context = context;
    }

    @Override
    public void enterMessageBlock(ProtoParser.MessageBlockContext ctx) {
        Message message = new Message();
        context.push(message);
    }

    @Override
    public void exitMessageBlock(ProtoParser.MessageBlockContext ctx) {
        Message message = context.pop(Message.class);
        AbstractUserTypeContainer messageParentBuilder = context.peek(AbstractUserTypeContainer.class);
        String name = ctx.NAME().getText();
        message.setName(name);
        messageParentBuilder.addMessage(message);
    }

    @Override
    public void enterMessageField(ProtoParser.MessageFieldContext ctx) {
        Field field = new Field();
        context.push(field);
    }

    @Override
    public void exitMessageField(ProtoParser.MessageFieldContext ctx) {
        Field field = context.pop(Field.class);
        Message message = context.peek(Message.class);
        String name = ctx.name().getText();
        String type = ctx.typeReference().getText();
        Integer tag = Integer.decode(ctx.INTEGER_VALUE().getText());
        updateModifier(ctx.fieldModifier(), field);
        field.setName(name);
        field.setTag(tag);
        field.setTypeName(type);
        message.addField(field);
    }

    @Override
    public void enterExtendBlock(ProtoParser.ExtendBlockContext ctx) {
    }

    @Override
    public void exitExtendBlock(ProtoParser.ExtendBlockContext ctx) {

    }

    @Override
    public void enterExtendBlockEntry(ProtoParser.ExtendBlockEntryContext ctx) {
        Extension extension = new Extension();
        context.push(extension);
    }

    @Override
    public void exitExtendBlockEntry(ProtoParser.ExtendBlockEntryContext ctx) {
        Extension extension = context.pop(Extension.class);
        ProtoParser.ExtendBlockContext extendBlockContext =
                (ProtoParser.ExtendBlockContext) ctx.getParent();
        String extendeeName = extendBlockContext.typeReference().getText();

        ExtensionContainer extensionContainer = context.peek(AbstractUserTypeContainer.class);
        String name = ctx.name().getText();
        String type = ctx.typeReference().getText();
        Integer tag = Integer.decode(ctx.INTEGER_VALUE().getText());
        updateModifier(ctx.fieldModifier(), extension);
        extension.setExtendeeName(extendeeName);
        extension.setName(name);
        extension.setTag(tag);
        extension.setTypeName(type);
        extensionContainer.addExtension(extension);
    }

    private void updateModifier(ProtoParser.FieldModifierContext modifierContext, Field field) {
        if (modifierContext != null) {
            if (modifierContext.OPTIONAL() != null) {
                field.setModifier(OPTIONAL);
            } else if (modifierContext.REQUIRED() != null) {
                field.setModifier(REQUIRED);
            } else if (modifierContext.REPEATED() != null) {
                field.setModifier(REPEATED);
            } else {
                throw new IllegalStateException("not implemented");
            }
        }
    }
}
