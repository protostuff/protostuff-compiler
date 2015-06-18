package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Message;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CustomOptionsValidator implements Validator {

    @Override
    public void validate(ProtoContext context) {
        ProtoWalker.newInstance(context)
                .onMessage(this::checkMessageOptions)
                .walk();
    }

    private void checkMessageOptions(ProtoContext context, Message message) {
        DynamicMessage options = message.getOptions();
        if (options.isEmpty()) {
            // nothing to check - skip this message
            return;
        }

    }
}
