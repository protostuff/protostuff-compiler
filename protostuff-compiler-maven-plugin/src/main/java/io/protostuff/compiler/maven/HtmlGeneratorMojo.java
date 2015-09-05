package io.protostuff.compiler.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import io.protostuff.compiler.ProtoCompiler;
import io.protostuff.compiler.generator.html.HtmlGenerator;
import io.protostuff.compiler.model.ModuleConfiguration;

import static java.util.Collections.singletonList;
import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Mojo(name = "html", defaultPhase = GENERATE_SOURCES)
public class HtmlGeneratorMojo extends AbstractGeneratorMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlGeneratorMojo.class);

    @Parameter(defaultValue = "${project.build.directory}/generated-html")
    private File target;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();

        ProtoCompiler compiler = new ProtoCompiler();
        final Path sourcePath = source.toPath();
        ModuleConfiguration.Builder builder = ModuleConfiguration.newBuilder()
                .name("html")
                .includePaths(singletonList(sourcePath))
                .template(HtmlGenerator.GENERATOR_NAME)
                .output(target.getAbsolutePath());
        PathMatcher protoMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.proto");
        try {
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (protoMatcher.matches(file)) {
                        String protoFile = sourcePath.relativize(file).toString();
                        builder.addProtoFile(protoFile);
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            LOGGER.error("Can not build source files list", e);
        }
        ModuleConfiguration moduleConfiguration = builder.build();

        LOGGER.debug("Module configuration = {}", moduleConfiguration);
        compiler.compile(moduleConfiguration);

    }

}
