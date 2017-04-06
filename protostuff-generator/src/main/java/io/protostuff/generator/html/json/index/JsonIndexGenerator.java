package io.protostuff.generator.html.json.index;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.UserTypeContainer;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonIndexGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonIndexGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public void compile(Module module) {
        List<JsonTreeNode> root = new ArrayList<>();
        module.getProtos()
                .forEach(proto -> root.add(ImmutableJsonTreeNode.builder()
                        .label(proto.getFilename())
                        .data(ImmutableNodeData.builder()
                                .ref(proto.getCanonicalName())
                                .type(NodeType.PROTO)
                                .build())
                        .children(processProto(proto))
                        .build()));
        String output = "data/index.json";
        write(module, output, root);
    }

    private List<JsonTreeNode> processProto(Proto proto) {
        List<JsonTreeNode> result = new ArrayList<>();
        proto.getServices()
                .forEach(service -> result.add(ImmutableJsonTreeNode.builder()
                        .label(service.getName())
                        .data(ImmutableNodeData.builder()
                                .type(NodeType.SERVICE)
                                .ref(service.getCanonicalName())
                                .build())
                        .build()));
        result.addAll(processContainer(proto));
        return result;
    }

    private List<JsonTreeNode> processContainer(UserTypeContainer proto) {
        List<JsonTreeNode> result = new ArrayList<>();
        proto.getEnums()
                .forEach(anEnum -> result.add(ImmutableJsonTreeNode.builder()
                        .label(anEnum.getName())
                        .data(ImmutableNodeData.builder()
                                .type(NodeType.ENUM)
                                .ref(anEnum.getCanonicalName())
                                .build())
                        .build()));
        proto.getMessages().stream()
                .filter(message -> !message.isMapEntry())
                .forEach(message -> {
                    ImmutableJsonTreeNode.Builder builder = ImmutableJsonTreeNode.builder();
                    builder.label(message.getName());
                    builder.data(ImmutableNodeData.builder()
                            .type(NodeType.MESSAGE)
                            .ref(message.getCanonicalName())
                            .build());
                    List<JsonTreeNode> children = processContainer(message);
                    if (!children.isEmpty()) {
                        builder.children(children);
                    }
                    result.add(builder.build());
                });
        return result;
    }
}
