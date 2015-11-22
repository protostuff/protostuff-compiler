package io.protostuff.generator.html.json.proto;

import javax.inject.Inject;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonProtoGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonProtoGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public String getName() {
        return "html-data-proto";
    }

    @Override
    public void compile(Module module) {
        module.getProtos().stream()
                .forEach(proto -> process(module, proto));
    }

    private void process(Module module, Proto proto) {
        String name = proto.getCanonicalName();
        ImmutableProtoDescriptor descriptor = ImmutableProtoDescriptor.builder()
                .name(proto.getName())
                .type(NodeType.PROTO)
                .canonicalName(proto.getCanonicalName())
                .filename(proto.getFilename())
                .description(proto.getComments())
                .build();
        write(module.getOutput() + "/data/proto/" + name + ".json", descriptor);
    }
}
