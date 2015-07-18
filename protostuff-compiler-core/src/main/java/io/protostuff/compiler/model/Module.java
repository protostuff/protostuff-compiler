package io.protostuff.compiler.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Module {

    private String name;
    private List<Proto> protos;
    private String output;

    public Module() {
    }

    public Module(Proto... protos) {
        this.protos = Arrays.asList(protos);
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<Proto> getProtos() {
        if (protos == null) {
            return Collections.emptyList();
        }
        return protos;
    }

    public void setProtos(List<Proto> protos) {
        this.protos = protos;
    }

    public void addProto(Proto proto) {
        if (protos == null) {
            protos = new ArrayList<>();
        }
        protos.add(proto);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
