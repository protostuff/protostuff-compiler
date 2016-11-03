package io.protostuff.generator.html.json.message;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.FieldModifier;
import io.protostuff.compiler.model.FieldType;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.UserTypeContainer;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;
import io.protostuff.generator.html.markdown.MarkdownProcessor;

import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonMessageGenerator extends AbstractJsonGenerator {

    private final MarkdownProcessor markdownProcessor;

    @Inject
    public JsonMessageGenerator(OutputStreamFactory outputStreamFactory, MarkdownProcessor markdownProcessor) {
        super(outputStreamFactory);
        this.markdownProcessor = markdownProcessor;
    }

    @Override
    public void compile(Module module) {
        module.getProtos()
                .forEach(proto -> rec(module, proto));
    }

    private void rec(Module module, UserTypeContainer container) {
        container.getMessages()
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
                .description(markdownProcessor.toHtml(message.getComments()))
                .addAllFields(message.getFields().stream()
                        .map(field -> {
                            ImmutableMessageField.Builder builder = ImmutableMessageField.builder()
                                    .name(field.getName())
                                    .typeId(field.getType().getCanonicalName())
                                    .modifier(getModifier(field))
                                    .tag(field.getTag())
                                    .description(createFieldDescription(field))
                                    .oneof(field.isOneofPart() ? field.getOneof().getName() : null);
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
        String output = "data/type/" + message.getCanonicalName() + ".json";
        write(module, output, descriptor);
    }

    private String createFieldDescription(Field field) {
        String comments;
        if (!field.getComments().isEmpty()) {
            comments = field.getComments();
        } else {
            comments = copyDescriptionFromFieldType(field);
        }
        return markdownProcessor.toHtml(comments);
    }

    private String copyDescriptionFromFieldType(Field field) {
        String comments;FieldType type = field.getType();
        if (type instanceof Message) {
            comments = ((Message) type).getComments();
        } else if (type instanceof Enum) {
            comments = ((Enum) type).getComments();
        } else {
            comments = "";
        }
        return comments;
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
