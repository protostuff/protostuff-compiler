package io.protostuff.generator.html.json.proto;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;

import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonProtoGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonProtoGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public void compile(Module module) {
        for (Proto proto : module.getProtos()) {
            process(module, proto);
        }
    }

    private void process(Module module, Proto proto) {
        String name = proto.getCanonicalName();
        ProtoDescriptor descriptor = new ProtoDescriptor();
        descriptor.setName(proto.getName());
        descriptor.setType(NodeType.PROTO);
        descriptor.setCanonicalName(proto.getCanonicalName());
        descriptor.setFilename(proto.getFilename());
        descriptor.setDescription(proto.getComments());
        write(module.getOutput() + "/data/proto/" + name + ".json", descriptor);
    }
}
