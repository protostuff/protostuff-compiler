package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.ExtensionRange;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;

import java.util.List;

/**
 * Extensions validator
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class ExtensionValidator implements Validator {

    @Override
    public void validate(ProtoContext protoContext) {
        ProtoWalker.newInstance(protoContext)
                .onMessage(this::checkExtensionTagsAreInRange)
                .walk();
    }

    public void checkExtensionTagsAreInRange(ProtoContext context, Message message) {
        List<Extension> extensions = message.getExtensions();
        List<ExtensionRange> ranges = message.getExtensionRanges();
        for (Extension extension : extensions) {
            List<Field> fields = extension.getFields();
            for (Field field : fields) {
                int tag = field.getTag();
                boolean inRange = false;
                for (ExtensionRange range : ranges) {
                    int min = range.getMin();
                    int max = range.getMax();
                    if (tag >= min && tag <= max) {
                        inRange = true;
                    }
                }
                if (!inRange) {
                    String format = "Extension field '%s' tag=%d is out of allowed range";
                    throw new ParserException(field, format, field.getName(), tag);
                }
            }
        }
    }
}
