package io.protostuff.compiler.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ModuleConfiguration {

    /**
     * Module name.
     */
    String name;

    /**
     * List of "include" folders used to search for proto files.
     */
    List<File> includePaths;

    /**
     * List of proto files to compile.
     */
    List<String> protoFiles;

    /**
     * Generator used to compile proto files. Currently following generators
     * are supported:
     * <p>
     * <ul>
     * <li><code>java</code> - produces Java source code that uses protostuff runtime;</li>
     * <li><code>html</code> - produces HTML documentation;</li>
     * <li><code>st4</code> - generic generator, you should provide custom template
     * (StringTemplate 4) using {@linkplain #getOptions()}.
     * </li>
     * </ul>
     */
    String generator;

    /**
     * Output directory.
     */
    String output;

    /**
     * Map of custom settings passed to the generator.
     */
    Map<String, Object> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getIncludePaths() {
        if (includePaths == null) {
            return Collections.emptyList();
        }
        return includePaths;
    }

    public void setIncludePaths(List<File> includePaths) {
        this.includePaths = includePaths;
    }

    public List<String> getProtoFiles() {
        if (protoFiles == null) {
            return Collections.emptyList();
        }
        return protoFiles;
    }

    public void addProtoFile(String proto) {
        if (protoFiles == null) {
            protoFiles = new ArrayList<String>();
        }
        protoFiles.add(proto);
    }

    public void setProtoFiles(List<String> protoFiles) {
        this.protoFiles = protoFiles;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
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
