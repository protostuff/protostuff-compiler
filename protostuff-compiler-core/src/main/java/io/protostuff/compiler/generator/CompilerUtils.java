package io.protostuff.compiler.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CompilerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompilerUtils.class);

    private final OutputStreamFactory outputStreamFactory;

    @Inject
    public CompilerUtils(OutputStreamFactory outputStreamFactory) {
        this.outputStreamFactory = outputStreamFactory;
    }


    public void copyResource(String name, String destinationFilename) {
        OutputStream outputStream = null;
        try {
            outputStream = outputStreamFactory.createStream(destinationFilename);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                throw new IllegalStateException("Can not obtain classloader instance from current thread");
            }
            URL resource = classLoader.getResource(name);
            if (resource != null) {
                try {
                    Path path = Paths.get(resource.toURI());
                    byte[] bytes = Files.readAllBytes(path);
                    outputStream.write(bytes);
                    outputStream.close();
                } catch (IOException | URISyntaxException e) {
                    throw new GeneratorException("Could not copy %s", e, name);
                }
            }
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Could not close file: " + name, e);
                }
            }
        }
    }

}
