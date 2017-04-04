package io.protostuff.compiler.parser;

import java.io.InputStream;
import javax.annotation.Nullable;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ClasspathFileReader implements FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathFileReader.class);

    public static InputStream readResource(String name) {
        String classpath = System.getProperty("java.class.path");
        LOGGER.trace("Reading {} from classpath={}", name, classpath);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            throw new IllegalStateException("Can not obtain classloader instance from current thread");
        }
        return classLoader.getResourceAsStream(name);
    }

    @Nullable
    @Override
    public CharStream read(String name) {
        try {
            InputStream resource = readResource(name);
            if (resource != null) {
                return CharStreams.fromStream(resource);
            }
        } catch (Exception e) {
            LOGGER.error("Could not read {}", name, e);
        }
        return null;
    }

}
