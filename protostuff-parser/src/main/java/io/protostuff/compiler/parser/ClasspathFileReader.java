package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import javax.annotation.Nullable;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ClasspathFileReader implements FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathFileReader.class);

    @Nullable
    @Override
    public CharStream read(String name) {
        try {
            String classpath = System.getProperty("java.class.path");
            LOGGER.trace("Reading {} from classpath={}", name, classpath);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                throw new IllegalStateException("Can not obtain classloader instance from current thread");
            }
            InputStream resource = classLoader.getResourceAsStream(name);
            if (resource != null) {
                return new ANTLRInputStream(resource);
            }
        } catch (Exception e) {
            LOGGER.error("Could not read {}", name, e);
        }
        return null;
    }

}
