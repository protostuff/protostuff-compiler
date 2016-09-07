package io.protostuff.compiler.maven;

import io.protostuff.compiler.model.ModuleConfiguration;
import io.protostuff.generator.ProtostuffCompiler;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static java.util.Collections.singletonList;
import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_TEST_SOURCES;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Mojo(name = "java",
        configurator = "include-project-dependencies",
        requiresDependencyResolution = COMPILE_PLUS_RUNTIME)
public class JavaGeneratorMojo extends AbstractGeneratorMojo {

    public static final String GENERATED_SOURCES = "/generated-sources/proto";
    public static final String GENERATED_TEST_SOURCES = "/generated-test-sources/proto";
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaGeneratorMojo.class);

    @Parameter
    private File target;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();

        ProtostuffCompiler compiler = new ProtostuffCompiler();
        final File sourcePath = getSourcePath();
        String output = calculateOutput();
        ModuleConfiguration moduleConfiguration = new ModuleConfiguration();
        moduleConfiguration.setName("java");
        moduleConfiguration.setIncludePaths(singletonList(sourcePath));
        moduleConfiguration.setGenerator("java");
        moduleConfiguration.setOutput(output);
        addProtoFiles(sourcePath, moduleConfiguration);

        LOGGER.debug("Module configuration = {}", moduleConfiguration);
        compiler.compile(moduleConfiguration);
        addGeneratedSourcesToProject(output);
    }

    private void addGeneratedSourcesToProject(String output) {
        // Include generated directory to the list of compilation sources
        if (GENERATE_TEST_SOURCES.id().equals(execution.getLifecyclePhase())) {
            project.addTestCompileSourceRoot(output);
        } else {
            project.addCompileSourceRoot(output);
        }
    }

    private String calculateOutput() {
        String output;
        if (target != null) {
            output = target.getAbsolutePath();
        } else {
            String phase = execution.getLifecyclePhase();
            String buildDirectory = project.getBuild().getDirectory();
            if (GENERATE_TEST_SOURCES.id().equals(phase)) {
                output = buildDirectory + GENERATED_TEST_SOURCES;
            } else {
                output = buildDirectory + GENERATED_SOURCES;
            }
        }
        LOGGER.debug("output = {}", output);
        return output;
    }

}
