package io.protostuff.generator.html.json.service;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonServiceGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonServiceGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public void compile(Module module) {
        for (Proto proto : module.getProtos()) {
            for (Service service : proto.getServices()) {
                process(module, service);
            }
        }
    }


    private void process(Module module, Service service) {
        List<io.protostuff.generator.html.json.service.ServiceMethod> methods = new ArrayList<io.protostuff.generator.html.json.service.ServiceMethod>();
        for (ServiceMethod method : service.getMethods()) {
            io.protostuff.generator.html.json.service.ServiceMethod sm = new io.protostuff.generator.html.json.service.ServiceMethod();
            sm.setName(method.getName());
            sm.setArgTypeId(method.getArgType().getCanonicalName());
            sm.setReturnTypeId(method.getReturnType().getCanonicalName());
            sm.setDescription(method.getComments());
            methods.add(sm);
        }
        ServiceDescriptor descriptor = new ServiceDescriptor();
        descriptor.setType(NodeType.SERVICE);
        descriptor.setName(service.getName());
        descriptor.setCanonicalName(service.getCanonicalName());
        descriptor.setDescription(service.getComments());
        descriptor.setMethods(methods);
        String output = module.getOutput() + "/data/type/" + service.getCanonicalName() + ".json";
        write(output, descriptor);
    }

}
