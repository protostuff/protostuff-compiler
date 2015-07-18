package io.protostuff.compiler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ModuleConfiguration {

    private final String name;
    private final List<String> protoFiles;

    private ModuleConfiguration(Builder builder) {
        name = builder.name;
        protoFiles = builder.protoFiles;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(ModuleConfiguration copy) {
        Builder builder = new Builder();
        builder.protoFiles = copy.protoFiles;
        builder.name = copy.name;
        return builder;
    }

    public List<String> getProtoFiles() {
        return protoFiles;
    }

    public String getName() {
        return name;
    }

    public static final class Builder {
        private String name;
        private List<String> protoFiles;

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
