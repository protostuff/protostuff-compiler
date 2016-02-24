package io.protostuff.generator.html.json.service;

import java.util.stream.Collectors;

import javax.inject.Inject;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Service;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonServiceGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonServiceGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public String getName() {
        return "html-data-service";
    }

    @Override
    public void compile(Module module) {
        module.getProtos().stream()
                .forEach(proto -> proto.getServices().stream()
                .forEach(service -> process(module, service)));
    }


    private void process(Module module, Service service) {
        ImmutableServiceDescriptor descriptor = ImmutableServiceDescriptor.builder()
                .type(NodeType.SERVICE)
                .name(service.getName())
                .canonicalName(service.getCanonicalName())
                .description(service.getComments())
                .addAllMethods(service.getMethods().stream()
                        .map(method -> ImmutableServiceMethod.builder()
                                .name(method.getName())
                                .argTypeId(method.getArgType().getCanonicalName())
                                .returnTypeId(method.getReturnType().getCanonicalName())
                                .description(method.getComments())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        String output = module.getOutput() + "/data/type/" + service.getCanonicalName() + ".json";
        write(output, descriptor);
    }

}
