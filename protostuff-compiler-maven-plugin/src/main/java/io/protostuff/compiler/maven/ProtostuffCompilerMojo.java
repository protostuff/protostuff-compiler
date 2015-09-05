package io.protostuff.compiler.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Mojo(name = "generate", defaultPhase = GENERATE_SOURCES)
public class ProtostuffCompilerMojo extends AbstractMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtostuffCompilerMojo.class);
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        StaticLoggerBinder.getSingleton().setMavenLog(this.getLog());
        LOGGER.info("Running protostuff compiler");
    }

}
