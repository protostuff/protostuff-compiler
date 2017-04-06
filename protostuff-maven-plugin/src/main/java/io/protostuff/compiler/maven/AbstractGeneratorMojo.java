package io.protostuff.compiler.maven;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_TEST_SOURCES;

import io.protostuff.generator.GeneratorException;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractGeneratorMojo extends AbstractMojo {

    private static final String GENERATED_SOURCES = "/generated-sources/proto";
    private static final String GENERATED_TEST_SOURCES = "/generated-test-sources/proto";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGeneratorMojo.class);
    /**
     * A list of patterns to include, e.g. {@code *.proto}.
     */
    @Parameter
    protected List<String> includes;
    @Parameter
    protected List<String> excludes;
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;
    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    private MojoExecution execution;
    @Parameter
    private File source;

    Path getSourcePath() {
        if (source != null) {
            return source.toPath();
        }
        String phase = execution.getLifecyclePhase();
        String basedir;
        basedir = getCanonicalPath(project.getBasedir());
        String sourcePath;
        if (GENERATE_TEST_SOURCES.id().equals(phase)) {
            sourcePath = basedir + "/src/test/proto/";
        } else {
            sourcePath = basedir + "/src/main/proto/";
        }
        return Paths.get(sourcePath);
    }

    private String getCanonicalPath(File file) {
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            throw new GeneratorException("Could not determine full path for %s", e, file);
        }
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        StaticLoggerBinder.getSingleton().setMavenLog(this.getLog());
    }

    List<String> findProtoFiles(final Path sourcePath) {
        List<String> protoFiles = new ArrayList<>();
        PathMatcher protoMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.proto");
        try {
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (protoMatcher.matches(file)) {
                        String protoFile = sourcePath.relativize(file).toString();
                        String normalizedPath = normalizeProtoPath(protoFile);
                        protoFiles.add(normalizedPath);
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            LOGGER.error("Can not build source files list", e);
        }
        return protoFiles;
    }

    private String normalizeProtoPath(String protoFilePath) {
        String normalizedPath;
        if (File.separatorChar == '\\') {
            normalizedPath = protoFilePath.replace('\\', '/');
        } else {
            normalizedPath = protoFilePath;
        }
        return normalizedPath;
    }

    String computeSourceOutputDir(@Nullable File target) {
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

    void addGeneratedSourcesToProject(String output) {
        // Include generated directory to the list of compilation sources
        if (GENERATE_TEST_SOURCES.id().equals(execution.getLifecyclePhase())) {
            project.addTestCompileSourceRoot(output);
        } else {
            project.addCompileSourceRoot(output);
        }
    }

}
