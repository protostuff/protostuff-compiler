package io.protostuff.compiler.maven;

import static java.util.Collections.singletonList;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;

import io.protostuff.compiler.model.ImmutableModuleConfiguration;
import io.protostuff.compiler.model.ModuleConfiguration;
import io.protostuff.generator.CompilerModule;
import io.protostuff.generator.ProtostuffCompiler;
import io.protostuff.generator.html.HtmlGenerator;
import io.protostuff.generator.html.StaticPage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * HTML generator MOJO.
 *
 * @author Kostiantyn Shchepanovskyi
 */
@Mojo(name = "html",
        threadSafe = true,
        configurator = "include-project-dependencies",
        requiresDependencyResolution = COMPILE_PLUS_RUNTIME)
public class HtmlGeneratorMojo extends AbstractGeneratorMojo {

    @Parameter(defaultValue = "${project.build.directory}/generated-html")
    private File target;

    @Parameter
    private List<StaticPage> pages = new ArrayList<>();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();
        ProtostuffCompiler compiler = new ProtostuffCompiler();
        final Path sourcePath = getSourcePath();
        List<String> protoFiles = findProtoFiles(sourcePath);
        ModuleConfiguration moduleConfiguration = ImmutableModuleConfiguration.builder()
                .name("html")
                .includePaths(singletonList(sourcePath))
                .generator(CompilerModule.HTML_COMPILER)
                .output(target.getAbsolutePath())
                .putOptions(HtmlGenerator.PAGES, pages)
                .addAllProtoFiles(protoFiles)
                .build();
        compiler.compile(moduleConfiguration);
    }


}
