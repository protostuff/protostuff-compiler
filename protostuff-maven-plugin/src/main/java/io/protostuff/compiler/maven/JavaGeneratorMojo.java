package io.protostuff.compiler.maven;

import static java.util.Collections.singletonList;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;

import io.protostuff.compiler.model.ImmutableModuleConfiguration;
import io.protostuff.compiler.model.ModuleConfiguration;
import io.protostuff.generator.ProtostuffCompiler;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Java generator MOJO.
 *
 * @author Kostiantyn Shchepanovskyi
 */
@Mojo(name = "java",
        threadSafe = true,
        configurator = "include-project-dependencies",
        requiresDependencyResolution = COMPILE_PLUS_RUNTIME)
public class JavaGeneratorMojo extends AbstractGeneratorMojo {

    @Parameter
    private File target;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();

        ProtostuffCompiler compiler = new ProtostuffCompiler();
        final Path sourcePath = getSourcePath();
        String output = computeSourceOutputDir(target);
        List<String> protoFiles = findProtoFiles(sourcePath);
        ModuleConfiguration moduleConfiguration = ImmutableModuleConfiguration.builder()
                .name("java")
                .includePaths(singletonList(sourcePath))
                .generator("java")
                .output(output)
                .protoFiles(protoFiles)
                .build();
        compiler.compile(moduleConfiguration);
        addGeneratedSourcesToProject(output);
    }

}
