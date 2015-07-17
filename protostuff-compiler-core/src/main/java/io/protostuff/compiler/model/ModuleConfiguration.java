package io.protostuff.compiler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ModuleConfiguration {

    // list of proto files that should be processed
    private final List<String> protoFiles;

    private ModuleConfiguration(Builder builder) {
        protoFiles = builder.protoFiles;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(ModuleConfiguration copy) {
        Builder builder = new Builder();
        builder.protoFiles = copy.protoFiles;
        return builder;
    }

    public List<String> getProtoFiles() {
        return protoFiles;
    }


    public static final class Builder {
        private List<String> protoFiles;

        private Builder() {
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
