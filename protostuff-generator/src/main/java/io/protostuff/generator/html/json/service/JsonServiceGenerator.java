package io.protostuff.generator.html.json.service;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Service;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;
import io.protostuff.generator.html.markdown.MarkdownProcessor;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonServiceGenerator extends AbstractJsonGenerator {

    private final MarkdownProcessor markdownProcessor;

    @Inject
    public JsonServiceGenerator(OutputStreamFactory outputStreamFactory, MarkdownProcessor markdownProcessor) {
        super(outputStreamFactory);
        this.markdownProcessor = markdownProcessor;
    }

    @Override
    public void compile(Module module) {
        module.getProtos()
                .forEach(proto -> proto.getServices()
                        .forEach(service -> process(module, service)));
    }


    private void process(Module module, Service service) {
        ImmutableServiceDescriptor descriptor = ImmutableServiceDescriptor.builder()
                .type(NodeType.SERVICE)
                .name(service.getName())
                .canonicalName(service.getCanonicalName())
                .description(markdownProcessor.toHtml(service.getComments()))
                .options(service.getOptions().toMap())
                .addAllMethods(service.getMethods().stream()
                        .map(method -> ImmutableServiceMethod.builder()
                                .name(method.getName())
                                .argTypeId(method.getArgType().getCanonicalName())
                                .returnTypeId(method.getReturnType().getCanonicalName())
                                .description(markdownProcessor.toHtml(method.getComments()))
                                .options(method.getOptions().toMap())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        String output = "data/type/" + service.getCanonicalName() + ".json";
        write(module, output, descriptor);
    }

}
