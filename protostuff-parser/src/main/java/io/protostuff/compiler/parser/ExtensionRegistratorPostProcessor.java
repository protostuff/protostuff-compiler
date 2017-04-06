package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Range;
import io.protostuff.compiler.model.UserTypeContainer;
import java.util.List;

/**
 * Post-processor that registers extensions in the extension registry.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class ExtensionRegistratorPostProcessor implements ProtoContextPostProcessor {

    @Override
    public void process(ProtoContext context) {
        registerExtensions(context, context.getProto());
    }

    private void registerExtensions(ProtoContext context, UserTypeContainer container) {
        ExtensionRegistry extensionRegistry = context.getExtensionRegistry();
        List<Extension> extensions = container.getDeclaredExtensions();
        for (Extension extension : extensions) {
            Message extendee = extension.getExtendee();
            List<Range> ranges = extendee.getExtensionRanges();
            List<Field> fields = extension.getFields();
            for (Field field : fields) {
                checkRanges(field, ranges);
            }
            String parentNamespace = container.getNamespace();
            extension.setNamespace(parentNamespace);
            extensionRegistry.registerExtension(extension);
        }
        for (Message message : container.getMessages()) {
            registerExtensions(context, message);
        }
    }

    private void checkRanges(Field field, List<Range> ranges) {
        int tag = field.getTag();
        boolean inRange = false;
        for (Range range : ranges) {
            int from = range.getFrom();
            int to = range.getTo();
            if (tag >= from && tag <= to) {
                inRange = true;
            }
        }
        if (!inRange) {
            String format = "Extension field '%s' tag=%d is out of allowed range";
            throw new ParserException(field, format, field.getName(), tag);
        }
    }

}
