package io.protostuff.generator.html.json.message;

import io.protostuff.compiler.model.*;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonMessageGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonMessageGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public void compile(Module module) {
        for (Proto proto : module.getProtos()) {
            rec(module, proto);
        }
    }

    private void rec(Module module, UserTypeContainer container) {
        for (Message message : container.getMessages()) {
            process(module, message);
            rec(module, message);
        }
    }

    private void process(Module module, Message message) {
        List<MessageField> fields = new ArrayList<MessageField>();
        for (Field field : message.getFields()) {
            MessageField messageField = new MessageField();
            messageField.setName(field.getName());
            messageField.setTypeId(field.getType().getCanonicalName());
            messageField.setModifier(getModifier(field));
            messageField.setTag(field.getTag());
            messageField.setDescription(field.getComments());
            messageField.setOneof(field.isOneofPart() ? field.getOneof().getName() : null);
            boolean isMap = field.isMap();
            if (isMap) {
                messageField.setMap(true);
                messageField.setMapKeyTypeId(getMapKeyType(field));
                messageField.setMapValueTypeId(getMapValueType(field));
            } else {
                messageField.setMap(false);
            }
            fields.add(messageField);
        }
        MessageDescriptor descriptor = new MessageDescriptor();
        descriptor.setType(NodeType.MESSAGE);
        descriptor.setName(message.getName());
        descriptor.setCanonicalName(message.getCanonicalName());
        descriptor.setDescription(message.getComments());
        descriptor.setFields(fields);
        String output = module.getOutput() + "/data/type/" + message.getCanonicalName() + ".json";
        write(output, descriptor);
    }

    private String getMapKeyType(Field field) {
        Message message = (Message) field.getType();
        Field key = message.getField("key");
        return key.getType().getCanonicalName();
    }

    private String getMapValueType(Field field) {
        Message message = (Message) field.getType();
        Field value = message.getField("value");
        return value.getType().getCanonicalName();
    }

    private MessageFieldModifier getModifier(Field field) {
        FieldModifier modifier = field.getModifier();
        String modifierString = modifier.toString();
        return MessageFieldModifier.fromString(modifierString);
    }
}
