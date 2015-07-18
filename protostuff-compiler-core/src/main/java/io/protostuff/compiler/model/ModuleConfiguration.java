package io.protostuff.compiler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ModuleConfiguration {

    private final String name;
    private final List<String> protoFiles;
    private final String output;

    private ModuleConfiguration(Builder builder) {
        name = builder.name;
        protoFiles = builder.protoFiles;
        output = builder.output;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(ModuleConfiguration copy) {
        Builder builder = new Builder();
        builder.protoFiles = copy.protoFiles;
        builder.name = copy.name;
        builder.output = copy.output;
        return builder;
    }

    public List<String> getProtoFiles() {
        return protoFiles;
    }

    public String getName() {
        return name;
    }

    public String getOutput() {
        return output;
    }

    public static final class Builder {
        private String name;
        private List<String> protoFiles;
        private String output;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder protoFiles(List<String> protoFiles) {
            this.protoFiles = protoFiles;
            return this;
        }

        public Builder output(String output) {
            this.output = output;
            return this;
        }

        public Builder addProtoFile(String protoFile) {
            if (protoFiles == null) {
                protoFiles = new ArrayList<>();
            }
            protoFiles.add(protoFile);
            return this;
        }

        public ModuleConfiguration build() {
            return new ModuleConfiguration(this);
        }
    }
}
