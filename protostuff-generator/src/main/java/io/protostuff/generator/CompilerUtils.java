package io.protostuff.generator;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
                String error = "Can not obtain classloader instance from current thread";
                throw new IllegalStateException(error);
            }
            try {
                InputStream stream = classLoader.getResourceAsStream(name);
                if (stream == null) {
                    String error = "Could not copy file, source file not found: " + name;
                    throw new IllegalStateException(error);
                }
                File path = new File(destinationFilename);
                FileUtils.copyInputStreamToFile(stream, path);
            } catch (IOException e) {
                throw new GeneratorException("Could not copy %s", e, name);
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
