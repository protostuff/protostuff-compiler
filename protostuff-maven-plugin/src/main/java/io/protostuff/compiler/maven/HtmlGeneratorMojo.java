package io.protostuff.compiler.maven;

import io.protostuff.compiler.model.ModuleConfiguration;
import io.protostuff.generator.CompilerModule;
import io.protostuff.generator.ProtostuffCompiler;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static java.util.Collections.singletonList;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Mojo(name = "html",
        configurator = "include-project-dependencies",
        requiresDependencyResolution = COMPILE_PLUS_RUNTIME)
public class HtmlGeneratorMojo extends AbstractGeneratorMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlGeneratorMojo.class);

    @Parameter(defaultValue = "${project.build.directory}/generated-html")
    private File target;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();

        ProtostuffCompiler compiler = new ProtostuffCompiler();
        final File sourcePath = getSourcePath();
        ModuleConfiguration moduleConfiguration = new ModuleConfiguration();
        moduleConfiguration.setName("html");
        moduleConfiguration.setIncludePaths(singletonList(sourcePath));
        moduleConfiguration.setGenerator(CompilerModule.HTML_COMPILER);
        moduleConfiguration.setOutput(target.getAbsolutePath());
        addProtoFiles(sourcePath, moduleConfiguration);

        LOGGER.debug("Module configuration = {}", moduleConfiguration);
        compiler.compile(moduleConfiguration);

    }

}
