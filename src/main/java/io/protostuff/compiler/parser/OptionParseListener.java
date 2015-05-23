package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.AbstractDescriptor;
import io.protostuff.compiler.model.DynamicMessage;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class OptionParseListener extends ProtoParserBaseListener {

    public static final int HEX = 16;
    public static final int OCT = 8;
    public static final int DECIMAL = 10;
    private final ProtoContext context;

    private final Deque<DynamicMessage> textFormatStack;
    private DynamicMessage currentTextFormat;
    private DynamicMessage lastTextFormat;

    public OptionParseListener(ProtoContext context) {
        this.context = context;
        this.textFormatStack = new ArrayDeque<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void exitOption(ProtoParser.OptionContext ctx) {
        String optionName;
        AbstractDescriptor declaration = context.peek(AbstractDescriptor.class);
        ProtoParser.OptionValueContext optionValueContext = ctx.optionValue();
        optionName = ctx.optionName().getText();
        DynamicMessage.Value optionValue = getOptionValue(optionValueContext);
        declaration.getOptions().set(optionName, optionValue);
    }

    private DynamicMessage.Value getOptionValue(ProtoParser.OptionValueContext optionValueContext) {
        DynamicMessage.Value optionValue;
        if (optionValueContext.textFormat() != null) {
            optionValue = DynamicMessage.Value.create(lastTextFormat);
        } else if (optionValueContext.BOOLEAN_VALUE() != null) {
            String text = optionValueContext.BOOLEAN_VALUE().getText();
            boolean value = Boolean.parseBoolean(text);
            optionValue = DynamicMessage.Value.create(value);
        } else if (optionValueContext.INTEGER_VALUE() != null) {
            String text = optionValueContext.INTEGER_VALUE().getText();
            optionValue = parseInteger(text);
        } else if (optionValueContext.STRING_VALUE() != null) {
            String text = optionValueContext.STRING_VALUE().getText();
            // TODO: unescape
            optionValue = DynamicMessage.Value.create(Util.removeFirstAndLastChar(text));
        } else if (optionValueContext.NAME() != null) {
            String text = optionValueContext.NAME().getText();
            optionValue = DynamicMessage.Value.create(text);
        } else if (optionValueContext.FLOAT_VALUE() != null) {
            String text = optionValueContext.FLOAT_VALUE().getText();
            double value;
            if ("inf".equals(text)) {
                value = Double.POSITIVE_INFINITY;
            } else if ("-inf".equals(text)) {
                value = Double.NEGATIVE_INFINITY;
            } else {
                value = Double.parseDouble(text);
            }
            optionValue = DynamicMessage.Value.create(value);
        } else {
            throw new IllegalStateException();
        }
        return optionValue;
    }

    private DynamicMessage.Value parseInteger(String text) {
        long value;
        try {
            value = Long.decode(text);
        } catch (NumberFormatException e) {
            // For values like "FFFFFFFFFFFFFFFF" - valid unsigned int64 in hex
            // but Long.decode gives NumberFormatException (but we should
            // accept such numbers according to protobuf spec - )
            if (text.startsWith("0x")) {
                BigInteger num = new BigInteger(text.substring(2), HEX);
                value = num.longValue();
            } else if (text.startsWith("0")) {
                BigInteger num = new BigInteger(text.substring(1), OCT);
                value = num.longValue();
            } else {
                BigInteger num = new BigInteger(text, DECIMAL);
                value = num.longValue();
            }
        }
        return DynamicMessage.Value.create(value);
    }

    @Override
    public void enterTextFormat(ProtoParser.TextFormatContext ctx) {
        if (currentTextFormat != null) {
            textFormatStack.push(currentTextFormat);
        }
        currentTextFormat = new DynamicMessage();
    }

    @Override
    public void exitTextFormat(ProtoParser.TextFormatContext ctx) {
        lastTextFormat = currentTextFormat;
        if (textFormatStack.peek() != null) {
            currentTextFormat = textFormatStack.pop();
        } else {
            currentTextFormat = null;
        }
    }

    @Override
    public void exitTextFormatEntry(ProtoParser.TextFormatEntryContext ctx) {
        String name = ctx.textFormatOptionName().getText();
        DynamicMessage.Value value = getTextFormatOptionValue(ctx);
        currentTextFormat.set(name, value);
    }

    private DynamicMessage.Value getTextFormatOptionValue(ProtoParser.TextFormatEntryContext ctx) {
        DynamicMessage.Value optionValue;
        if (ctx.textFormat() != null) {
            optionValue = DynamicMessage.Value.create(lastTextFormat);
        } else if (ctx.textFormatOptionValue().BOOLEAN_VALUE() != null) {
            String text = ctx.textFormatOptionValue().BOOLEAN_VALUE().getText();
            boolean value = Boolean.parseBoolean(text);
            optionValue = DynamicMessage.Value.create(value);
        } else if (ctx.textFormatOptionValue().INTEGER_VALUE() != null) {
            String text = ctx.textFormatOptionValue().INTEGER_VALUE().getText();
            optionValue = parseInteger(text);
        } else if (ctx.textFormatOptionValue().STRING_VALUE() != null) {
            String text = ctx.textFormatOptionValue().STRING_VALUE().getText();
            // TODO: unescape
            optionValue = DynamicMessage.Value.create(Util.removeFirstAndLastChar(text));
        } else if (ctx.textFormatOptionValue().NAME() != null) {
            String text = ctx.textFormatOptionValue().NAME().getText();
            optionValue = DynamicMessage.Value.create(text);
        } else if (ctx.textFormatOptionValue().FLOAT_VALUE() != null) {
            String text = ctx.textFormatOptionValue().FLOAT_VALUE().getText();
            double value = Double.parseDouble(text);
            optionValue = DynamicMessage.Value.create(value);
        } else {
            throw new IllegalStateException();
        }
        return optionValue;
    }

}
