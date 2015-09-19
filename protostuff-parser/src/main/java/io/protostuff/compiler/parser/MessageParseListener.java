package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.BufferedTokenStream;

import io.protostuff.compiler.model.AbstractUserTypeContainer;
import io.protostuff.compiler.model.Element;
import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.ExtensionContainer;
import io.protostuff.compiler.model.ExtensionRange;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.FieldContainer;
import io.protostuff.compiler.model.Group;
import io.protostuff.compiler.model.GroupContainer;
import io.protostuff.compiler.model.Map;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.MessageContainer;
import io.protostuff.compiler.model.Oneof;
import io.protostuff.compiler.model.UserTypeContainer;

import static io.protostuff.compiler.model.FieldModifier.OPTIONAL;
import static io.protostuff.compiler.model.FieldModifier.REPEATED;
import static io.protostuff.compiler.model.FieldModifier.REQUIRED;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageParseListener extends AbstractProtoParserListener {

    public static final String MAX = "max";

    public MessageParseListener(BufferedTokenStream tokens, ProtoContext context) {
        super(tokens, context);
    }

    @Override
    public void enterMessageBlock(ProtoParser.MessageBlockContext ctx) {
        UserTypeContainer parent = context.peek(UserTypeContainer.class);
        Message message = new Message(parent);
        context.push(message);
    }

    @Override
    public void exitMessageBlock(ProtoParser.MessageBlockContext ctx) {
        Message message = context.pop(Message.class);
        MessageContainer container = context.peek(MessageContainer.class);
        String name = ctx.NAME().getText();
        message.setName(name);
        message.setSourceCodeLocation(getSourceCodeLocation(ctx));
        container.addMessage(message);
        attachComments(ctx, message, false);
    }

    @Override
    public void enterField(ProtoParser.FieldContext ctx) {
        FieldContainer parent = context.peek(FieldContainer.class);
        Field field = new Field(parent);
        context.push(field);
    }

    @Override
    public void exitField(ProtoParser.FieldContext ctx) {
        Field field = context.pop(Field.class);
        FieldContainer fieldContainer = context.peek(FieldContainer.class);
        String name = ctx.name().getText();
        String type = ctx.typeReference().getText();
        Integer tag = Integer.decode(ctx.INTEGER_VALUE().getText());
        updateModifier(ctx.fieldModifier(), field);
        field.setName(name);
        field.setTag(tag);
        field.setTypeName(type);
        field.setSourceCodeLocation(getSourceCodeLocation(ctx));
        fieldContainer.addField(field);
        attachComments(ctx, field, true);
    }

    @Override
    public void enterExtendBlock(ProtoParser.ExtendBlockContext ctx) {
        UserTypeContainer parent = context.peek(UserTypeContainer.class);
        Extension extension = new Extension(parent);
        context.push(extension);
    }

    @Override
    public void exitExtendBlock(ProtoParser.ExtendBlockContext ctx) {
        Extension extension = context.pop(Extension.class);
        String extendeeName = ctx.typeReference().getText();
        ExtensionContainer extensionContainer = context.peek(AbstractUserTypeContainer.class);
        extension.setExtendeeName(extendeeName);
        extension.setSourceCodeLocation(getSourceCodeLocation(ctx));
        extensionContainer.addDeclaredExtension(extension);
    }

    @Override
    public void enterGroupBlock(ProtoParser.GroupBlockContext ctx) {
        Element parent = context.peek(Element.class);
        if (parent instanceof Extension) {
            // hack: use extension's parent
            Group group = new Group(((Extension)parent).getParent());
            context.push(group);
        } else if (parent instanceof UserTypeContainer) {
            Group group = new Group(((UserTypeContainer)parent));
            context.push(group);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void exitGroupBlock(ProtoParser.GroupBlockContext ctx) {
        Group group = context.pop(Group.class);
        group.setName(ctx.name().getText());
        group.setSourceCodeLocation(getSourceCodeLocation(ctx));
        GroupContainer groupContainer = context.peek(GroupContainer.class);
        FieldContainer fieldContainer = context.peek(FieldContainer.class);
        Field field = new Field(fieldContainer);
        field.setName(group.getName().toLowerCase()); // same behavior as in protoc
        int tag = Integer.decode(ctx.INTEGER_VALUE().getText());
        field.setTag(tag);
        field.setTypeName(group.getName());
        field.setType(group);
        field.setSourceCodeLocation(getSourceCodeLocation(ctx));
        groupContainer.addGroup(group);
        fieldContainer.addField(field);
        attachComments(ctx, field, true);
    }

    @Override
    public void enterOneof(ProtoParser.OneofContext ctx) {
        Message parent = context.peek(Message.class);
        Oneof oneof = new Oneof(parent);
        context.push(oneof);
    }

    @Override
    public void exitOneof(ProtoParser.OneofContext ctx) {
        Oneof oneof = context.pop(Oneof.class);
        Message message = context.peek(Message.class);
        oneof.setName(ctx.name().getText());
        oneof.setSourceCodeLocation(getSourceCodeLocation(ctx));
        message.addOneof(oneof);
        attachComments(ctx, oneof, false);
    }

    @Override
    public void enterOneofField(ProtoParser.OneofFieldContext ctx) {
        FieldContainer parent = context.peek(FieldContainer.class);
        Field field = new Field(parent);
        context.push(field);
    }

    @Override
    public void exitOneofField(ProtoParser.OneofFieldContext ctx) {
        Field field = context.pop(Field.class);
        FieldContainer fieldContainer = context.peek(FieldContainer.class);
        String name = ctx.name().getText();
        String type = ctx.typeReference().getText();
        Integer tag = Integer.decode(ctx.INTEGER_VALUE().getText());
        field.setName(name);
        field.setTag(tag);
        field.setTypeName(type);
        field.setSourceCodeLocation(getSourceCodeLocation(ctx));
        fieldContainer.addField(field);
        attachComments(ctx, field, true);
    }

    @Override
    public void enterOneofGroup(ProtoParser.OneofGroupContext ctx) {
        Oneof parent = context.peek(Oneof.class);
        Group group = new Group(parent.getParent());
        context.push(group);
    }

    @Override
    public void exitOneofGroup(ProtoParser.OneofGroupContext ctx) {
        Group group = context.pop(Group.class);
        group.setSourceCodeLocation(getSourceCodeLocation(ctx));
        GroupContainer container = context.peek(GroupContainer.class);
        container.addGroup(group);
    }

    @Override
    public void enterMap(ProtoParser.MapContext ctx) {
        Message parent = context.peek(Message.class);
        Field field = new Field(parent);
        context.push(field);
    }

    @Override
    public void exitMap(ProtoParser.MapContext ctx) {
        Field field = context.pop(Field.class);
        Message message = context.peek(Message.class);
        String name = ctx.name().getText();
        String keyType = ctx.mapKey().getText();
        String valueType = ctx.mapValue().getText();
        Map map = new Map(message);
        map.setName(name);
        map.setSourceCodeLocation(getSourceCodeLocation(ctx));
        map.setKeyTypeName(keyType);
        map.setValueTypeName(valueType);
        Integer tag = Integer.decode(ctx.tag().getText());
        field.setName(name);
        field.setTag(tag);
        field.setTypeName(name);
        field.setType(map);
        field.setSourceCodeLocation(getSourceCodeLocation(ctx));
        message.addField(field);
        message.addMap(map);
        attachComments(ctx, field, true);
    }

    @Override
    public void exitExtensions(ProtoParser.ExtensionsContext ctx) {
        String fromString = ctx.from().getText();
        String toString = ctx.to() == null ? fromString : ctx.to().getText();
        int from = Integer.decode(fromString);
        int to;
        if (MAX.equals(toString)) {
            to = Field.MAX_TAG_VALUE;
        } else {
            to = Integer.decode(toString);
        }
        Message message = context.peek(Message.class);
        ExtensionRange range = new ExtensionRange(message, from, to);
        range.setSourceCodeLocation(getSourceCodeLocation(ctx));
        message.addExtensionRange(range);
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
