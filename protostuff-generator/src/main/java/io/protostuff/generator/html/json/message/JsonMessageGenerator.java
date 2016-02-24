package io.protostuff.generator.html.json.message;

import java.util.stream.Collectors;

import javax.inject.Inject;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.FieldModifier;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.UserTypeContainer;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonMessageGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonMessageGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public String getName() {
        return "html-data-message";
    }

    @Override
    public void compile(Module module) {
        module.getProtos().stream()
                .forEach(proto -> rec(module, proto));
    }

    private void rec(Module module, UserTypeContainer container) {
        container.getMessages().stream()
                .forEach(message -> {
                    process(module, message);
                    rec(module, message);
                });
    }

    private void process(Module module, Message message) {
        ImmutableMessageDescriptor descriptor = ImmutableMessageDescriptor.builder()
                .type(NodeType.MESSAGE)
                .name(message.getName())
                .canonicalName(message.getCanonicalName())
                .description(message.getComments())
                .addAllFields(message.getFields().stream()
                        .map(field -> {
                            ImmutableMessageField.Builder builder = ImmutableMessageField.builder()
                                    .name(field.getName())
                                    .typeId(field.getType().getCanonicalName())
                                    .modifier(getModifier(field))
                                    .tag(field.getTag())
                                    .description(field.getComments());
                            boolean isMap = field.isMap();
                            if (isMap) {
                                builder.isMap(true);
                                builder.mapKeyTypeId(getMapKeyType(field));
                                builder.mapValueTypeId(getMapValueType(field));
                            }
                            return builder.build();
                        })
                        .collect(Collectors.toList()))
                .build();
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
