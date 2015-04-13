package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.AbstractUserTypeContainer;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.MessageField;

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
        String name = ctx.NAME().getText();
        String type = ctx.fieldType().getText();
        Integer tag = Integer.decode(ctx.INTEGER_VALUE().getText());
        messageField.setName(name);
        messageField.setTag(tag);
        messageField.setTypeName(type);
        message.addField(messageField);
    }
}
