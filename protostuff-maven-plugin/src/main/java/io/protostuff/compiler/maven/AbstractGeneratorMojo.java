package io.protostuff.compiler.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.impl.StaticLoggerBinder;

import java.io.File;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractGeneratorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;

    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    protected MojoExecution execution;

    @Parameter(defaultValue = "${project.basedir}/src/main/proto")
    protected File source;

    /**
     * A list of patterns to include, e.g. {@code *.proto}.
     */
    @Parameter
    protected List<String> includes;

    @Parameter
    protected List<String> excludes;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        StaticLoggerBinder.getSingleton().setMavenLog(this.getLog());
    }

}
