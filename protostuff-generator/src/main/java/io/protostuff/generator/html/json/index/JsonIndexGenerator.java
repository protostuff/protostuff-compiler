package io.protostuff.generator.html.json.index;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.UserTypeContainer;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public final class JsonIndexGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonIndexGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public void compile(Module module) {
        List<JsonTreeNode> root = new ArrayList<>();
        module.getProtos()
                .forEach(proto -> root.add(JsonTreeNode.newBuilder()
                        .label(proto.getFilename())
                        .data(NodeData.newBuilder()
                                .ref(proto.getCanonicalName())
                                .type(NodeType.PROTO)
                                .build())
                        .children(processProto(proto))
                        .build()));
        String output = module.getOutput() + "/data/index.json";
        write(output, root);
    }

    private List<JsonTreeNode> processProto(Proto proto) {
        List<JsonTreeNode> result = new ArrayList<>();
        proto.getServices()
                .forEach(service -> result.add(JsonTreeNode.newBuilder()
                        .label(service.getName())
                        .data(NodeData.newBuilder()
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
                .forEach(anEnum -> result.add(JsonTreeNode.newBuilder()
                        .label(anEnum.getName())
                        .data(NodeData.newBuilder()
                                .type(NodeType.ENUM)
                                .ref(anEnum.getCanonicalName())
                                .build())
                        .build()));
        proto.getMessages().stream()
                .filter(message -> !message.isMapEntry())
                .forEach(message -> {
                    JsonTreeNode.Builder builder = JsonTreeNode.newBuilder();
                    builder.label(message.getName());
                    builder.data(NodeData.newBuilder()
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
