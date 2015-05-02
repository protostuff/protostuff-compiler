package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.AbstractUserTypeContainer;
import io.protostuff.compiler.model.FieldModifier;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.MessageField;

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
        MessageField messageField = new MessageField();
        context.push(messageField);
    }

    @Override
    public void exitMessageField(ProtoParser.MessageFieldContext ctx) {
        MessageField messageField = context.pop(MessageField.class);
        Message message = context.peek(Message.class);
        String name = ctx.fieldName().getText();
        String type = ctx.typeReference().getText();
        Integer tag = Integer.decode(ctx.INTEGER_VALUE().getText());
        FieldModifier modifier = FieldModifier.DEFAULT;
        ProtoParser.FieldModifierContext modifierContext = ctx.fieldModifier();
        if (modifierContext != null) {
            if (modifierContext.OPTIONAL() != null) {
                modifier = OPTIONAL;
            } else if (modifierContext.REQUIRED() != null) {
                modifier = REQUIRED;
            } else if (modifierContext.REPEATED() != null) {
                modifier = REPEATED;
            } else {
                throw new IllegalStateException("not implemented");
            }
        }
        messageField.setName(name);
        messageField.setTag(tag);
        messageField.setTypeName(type);
        messageField.setModifier(modifier);
        message.addField(messageField);
    }
}
