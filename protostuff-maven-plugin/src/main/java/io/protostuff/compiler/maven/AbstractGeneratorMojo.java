package io.protostuff.compiler.maven;

import com.google.common.base.Throwables;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.impl.StaticLoggerBinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_TEST_SOURCES;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractGeneratorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;

    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    protected MojoExecution execution;

    @Parameter
    protected File source;

    /**
     * A list of patterns to include, e.g. {@code *.proto}.
     */
    @Parameter
    protected List<String> includes;

    @Parameter
    protected List<String> excludes;

    protected Path getSourcePath() {
        if (source != null) {
            return source.toPath();
        }
        String phase = execution.getLifecyclePhase();
        String basedir;
        try {
            basedir = project.getBasedir().getCanonicalPath();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
        String sourcePath;
        if (GENERATE_TEST_SOURCES.id().equals(phase)) {
            sourcePath = basedir + "/src/test/proto/";
        } else {
            sourcePath = basedir + "/src/main/proto/";
        }
        return Paths.get(sourcePath);
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        StaticLoggerBinder.getSingleton().setMavenLog(this.getLog());
    }

    protected String normalizeProtoPath(String protoFilePath) {
        String normalizedPath;
        if (File.separatorChar == '\\') {
            normalizedPath = protoFilePath.replace('\\', '/');
        } else {
            normalizedPath = protoFilePath;
        }
        return normalizedPath;
    }

}
