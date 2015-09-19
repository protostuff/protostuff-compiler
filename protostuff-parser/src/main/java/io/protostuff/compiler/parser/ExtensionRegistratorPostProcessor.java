package io.protostuff.compiler.parser;

import java.util.List;

import io.protostuff.compiler.model.Extension;
import io.protostuff.compiler.model.ExtensionRange;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.UserTypeContainer;

/**
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
            List<ExtensionRange> ranges = extendee.getExtensionRanges();
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
            String parentNamespace = container.getNamespace();
            extension.setNamespace(parentNamespace);
            extensionRegistry.registerExtension(extension);
        }
        for (Message message : container.getMessages()) {
            registerExtensions(context, message);
        }
    }

}
