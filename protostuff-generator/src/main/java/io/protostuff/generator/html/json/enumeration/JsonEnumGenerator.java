package io.protostuff.generator.html.json.enumeration;

import java.util.stream.Collectors;

import javax.inject.Inject;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.UserTypeContainer;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.ImmutableUsageItem;
import io.protostuff.generator.html.json.UsageType;
import io.protostuff.generator.html.json.index.NodeType;
import io.protostuff.generator.html.markdown.MarkdownProcessor;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonEnumGenerator extends AbstractJsonGenerator {

    private final MarkdownProcessor markdownProcessor;

    @Inject
    public JsonEnumGenerator(OutputStreamFactory outputStreamFactory, MarkdownProcessor markdownProcessor) {
        super(outputStreamFactory);
        this.markdownProcessor = markdownProcessor;
    }

    @Override
    public void compile(Module module) {
        module.getProtos()
                .forEach(proto -> rec(module, proto));
    }

    private void rec(Module module, UserTypeContainer container) {
        container.getEnums()
                .forEach(anEnum -> process(module, anEnum));
        container.getMessages()
                .forEach(message -> rec(module, message));
    }

    private void process(Module module, Enum anEnum) {
        ImmutableEnumDescriptor descriptor = ImmutableEnumDescriptor.builder()
                .type(NodeType.ENUM)
                .name(anEnum.getName())
                .canonicalName(anEnum.getCanonicalName())
                .description(markdownProcessor.toHtml(anEnum.getComments()))
                .options(anEnum.getOptions().toMap())
                .usages(module.usageIndex().getUsages(anEnum).stream()
                        .map(type -> ImmutableUsageItem.builder()
                                .ref(type.getCanonicalName())
                                .type(UsageType.from(type))
                                .build())
                        .collect(Collectors.toList()))
                .addAllConstants(anEnum.getConstants().stream()
                        .map(enumConstant -> ImmutableEnumConstant.builder()
                                .name(enumConstant.getName())
                                .value(enumConstant.getValue())
                                .description(markdownProcessor.toHtml(enumConstant.getComments()))
                                .options(enumConstant.getOptions().toMap())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        String output = "data/type/" + anEnum.getCanonicalName() + ".json";
        write(module, output, descriptor);
    }
}
