package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.AbstractDescriptor;
import io.protostuff.compiler.model.OptionValue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class OptionParseListener extends ProtoParserBaseListener {

    private final ProtoContext context;
    private final Deque<Map<String, Object>> textFormatStack;

    private Map<String, Object> currentTextFormat;
    private Map<String, Object> lastTextFormat;

    public OptionParseListener(ProtoContext context) {
        this.context = context;
        this.textFormatStack = new ArrayDeque<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void exitOption(ProtoParser.OptionContext ctx) {
        OptionType optionType;
        String optionSubName = null;
        String optionName;
        AbstractDescriptor declaration = context.peek(AbstractDescriptor.class);
        ProtoParser.OptionValueContext optionValueContext = ctx.optionValue();
        ProtoParser.OptionNameContext optionNameContext = ctx.optionName();
        if (optionNameContext.name().size() == 1) {
            optionType = OptionType.STANDARD;
            optionName = optionNameContext.getText();
        } else {
            optionType = OptionType.CUSTOM;
            optionName = optionNameContext.getText();
        }
        Object optionValue = getOptionValue(optionValueContext);
        if (optionType == OptionType.STANDARD) {
            declaration.addOption(optionName, optionValue);
        } else {
//            if (optionSubName != null) {
//                Object customOptionValue = declaration.getOption(optionName);
//                Map<String, Object> map;
//                if (customOptionValue instanceof Map) {
//                    map = (Map<String, Object>) customOptionValue;
//                } else if (customOptionValue == null) {
//                    map = new HashMap<>();
//                    declaration.addOption(optionName, map);
//                } else {
//                    throw new IllegalStateException("custom option");
//                }
//                putValue(map, optionSubName, optionValue);
//            } else {
                declaration.addOption(optionName, optionValue);
//            }
        }
    }

    private Object getOptionValue(ProtoParser.OptionValueContext optionValueContext) {
        Object optionValue;
       if (optionValueContext.textFormat() != null) {
            optionValue = lastTextFormat;
        } else {
            optionValue = new OptionValue(optionValueContext.getText());
        }
        return optionValue;
    }

    @SuppressWarnings("unchecked")
    private void putValue(Map<String, Object> map, String name, Object value) {
        int dotIndex = name.indexOf('.');
        if (dotIndex == -1) {
            map.put(name, value);
        } else {
            String prefix = name.substring(0, dotIndex);
            Map<String, Object> node;
            if (map.containsKey(prefix)) {
                node = (Map<String, Object>) map.get(prefix);
            } else {
                node = new HashMap<>();
                map.put(prefix, node);
            }
            putValue(node, name.substring(dotIndex + 1, name.length()), value);
        }
    }

    @Override
    public void enterTextFormat(ProtoParser.TextFormatContext ctx) {
        if (currentTextFormat != null) {
            textFormatStack.push(currentTextFormat);
        }
        currentTextFormat = new HashMap<>();
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
//        Object optionValue = getOptionValue(ctx.textFormatOptionValue());
//        currentTextFormat.put(name, optionValue);
    }

    private enum OptionType {
        STANDARD,
        CUSTOM
    }
}
