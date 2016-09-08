package io.protostuff.generator.html.json.enumeration;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.EnumConstant;
import io.protostuff.compiler.model.*;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonEnumGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonEnumGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public void compile(Module module) {
        for (Proto proto : module.getProtos()) {
            rec(module, proto);
        }
    }

    private void rec(Module module, UserTypeContainer container) {
        for (Enum anEnum : container.getEnums()) {
            process(module, anEnum);
        }
        for (Message message : container.getMessages()) {
            rec(module, message);
        }
    }

    private void process(Module module, Enum anEnum) {
        List<io.protostuff.generator.html.json.enumeration.EnumConstant> constants = new ArrayList<io.protostuff.generator.html.json.enumeration.EnumConstant>();
        for (EnumConstant enumConstant : anEnum.getConstants()) {
            io.protostuff.generator.html.json.enumeration.EnumConstant c = new io.protostuff.generator.html.json.enumeration.EnumConstant();
            c.setName(enumConstant.getName());
            c.setValue(enumConstant.getValue());
            c.setDescription(enumConstant.getComments());
            constants.add(c);
        }
        EnumDescriptor descriptor = new EnumDescriptor();
        descriptor.setType(NodeType.ENUM);
        descriptor.setName(anEnum.getName());
        descriptor.setCanonicalName(anEnum.getCanonicalName());
        descriptor.setDescription(anEnum.getComments());
        descriptor.setConstants(constants);
        String output = module.getOutput() + "/data/type/" + anEnum.getCanonicalName() + ".json";
        write(output, descriptor);
    }
}
