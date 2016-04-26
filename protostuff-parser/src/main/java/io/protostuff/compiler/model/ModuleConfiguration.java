package io.protostuff.compiler.model;

import org.immutables.value.Value;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
public interface ModuleConfiguration {

    /**
     * Module name.
     */
    String getName();

    /**
     * List of "include" folders used to search for proto files.
     */
    List<Path> getIncludePaths();

    /**
     * List of proto files to compile.
     */
    List<String> getProtoFiles();

    /**
     * Generator used to compile proto files. Currently following generators
     * are supported:
     *
     * <ul>
     *     <li><code>java</code> - produces Java source code that uses protostuff runtime;</li>
     *     <li><code>html</code> - produces HTML documentation;</li>
     *     <li><code>st4</code> - generic generator, you should provide custom template
     *     (StringTemplate 4) using {@linkplain #getOptions()}.
     *     </li>
     * </ul>
     */
    String getGenerator();

    /**
     * Output directory.
     */
    String getOutput();

    /**
     * Map of custom settings passed to the generator.
     */
    Map<String, String> getOptions();

}
