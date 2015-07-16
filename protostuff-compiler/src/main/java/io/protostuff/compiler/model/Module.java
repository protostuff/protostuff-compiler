package io.protostuff.compiler.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Module {

    private List<Proto> protos;

    public Module() {
    }

    public Module(Proto... protos) {
        this.protos = Arrays.asList(protos);
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
        return "unnamed";
    }
}
