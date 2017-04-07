package io.protostuff.generator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility functions for code generators.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class CompilerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompilerUtils.class);

    private final OutputStreamFactory outputStreamFactory;

    @Inject
    public CompilerUtils(OutputStreamFactory outputStreamFactory) {
        this.outputStreamFactory = outputStreamFactory;
    }


    /**
     * Copy a classpath resource to the given location on a filesystem.
     */
    public void copyResource(String name, String destinationFilename) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            String error = "Can not obtain classloader instance";
            throw new IllegalStateException(error);
        }
        try (InputStream stream = classLoader.getResourceAsStream(name)) {
            if (stream == null) {
                String error = "Could not copy file, source file not found: " + name;
                throw new IllegalStateException(error);
            }
            Path path = Paths.get(destinationFilename);
            FileUtils.copyInputStreamToFile(stream, path.toFile());
        } catch (IOException e) {
            throw new GeneratorException("Could not copy %s", e, name);
        }
    }

}
