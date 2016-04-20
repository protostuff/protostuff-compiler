package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ModuleConfiguration {

    private final String name;
    private final List<Path> includePaths;
    private final List<String> protoFiles;
    private final String generator;
    private final String template;
    private final String initializer;
    private final String output;

    private ModuleConfiguration(Builder builder) {
        name = builder.name;
        includePaths = builder.includePaths;
        protoFiles = builder.protoFiles;
        generator = builder.generator;
        template = builder.template;
        initializer = builder.initializer;
        output = builder.output;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(ModuleConfiguration copy) {
        Builder builder = new Builder();
        builder.name = copy.name;
        builder.includePaths = copy.includePaths;
        builder.protoFiles = copy.protoFiles;
        builder.generator = copy.generator;
        builder.template = copy.template;
        builder.initializer = copy.initializer;
        builder.output = copy.output;
        return builder;
    }

    public List<String> getProtoFiles() {
        return protoFiles;
    }

    public String getName() {
        return name;
    }

    public String getGenerator() {
        return generator;
    }

    public String getTemplate() {
        return template;
    }

    public String getOutput() {
        return output;
    }

    public List<Path> getIncludePaths() {
        return includePaths;
    }

    public String getInitializer() {
        return initializer;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("includePaths", includePaths)
                .add("protoFiles", protoFiles)
                .add("template", template)
                .add("initializer", initializer)
                .add("generator", generator)
                .add("output", output)
                .toString();
    }


    public static final class Builder {

        private String name;
        private List<Path> includePaths;
        private List<String> protoFiles;
        private String generator;
        private String template;
        private String initializer;
        private String output;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder includePaths(List<Path> includePaths) {
            this.includePaths = includePaths;
            return this;
        }

        public Builder protoFiles(List<String> protoFiles) {
            this.protoFiles = protoFiles;
            return this;
        }

        public Builder generator(String generator) {
            this.generator = generator;
            return this;
        }

        public Builder template(String val) {
            template = val;
            return this;
        }

        public Builder initializer(String val) {
            initializer = val;
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
