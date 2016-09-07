package io.protostuff.compiler.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Module {

    String name;

    List<Proto> protos;

    String output;

    Map<String, Object> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Map<String, Object> getOptions() {
        if (options == null) {
            return Collections.emptyMap();
        }
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }
}
