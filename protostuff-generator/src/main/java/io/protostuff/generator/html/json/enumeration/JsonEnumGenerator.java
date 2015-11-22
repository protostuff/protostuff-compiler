package io.protostuff.generator.html.json.enumeration;

import java.util.stream.Collectors;

import javax.inject.Inject;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.UserTypeContainer;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonEnumGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonEnumGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public String getName() {
        return "html-data-enum";
    }

    @Override
    public void compile(Module module) {
        module.getProtos().stream()
                .forEach(proto -> rec(module, proto));
    }

    private void rec(Module module, UserTypeContainer container) {
        container.getEnums().stream()
                .forEach(anEnum -> process(module, anEnum));
        container.getMessages().stream()
                .forEach(message -> rec(module, message));
    }

    private void process(Module module, Enum anEnum) {
        ImmutableEnumDescriptor descriptor = ImmutableEnumDescriptor.builder()
                .type(NodeType.ENUM)
                .name(anEnum.getName())
                .canonicalName(anEnum.getCanonicalName())
                .description(anEnum.getComments())
                .addAllConstants(anEnum.getConstants().stream()
                        .map(enumConstant -> ImmutableEnumConstant.builder()
                                .name(enumConstant.getName())
                                .value(enumConstant.getValue())
                                .description(enumConstant.getComments())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        String output = module.getOutput() + "/data/type/" + anEnum.getCanonicalName() + ".json";
        write(output, descriptor);
    }
}
