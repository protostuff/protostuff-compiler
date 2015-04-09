package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ClasspathFileReader implements FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathFileReader.class);

    @Nullable
    @Override
    public CharStream read(String name) {
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(name);
            if (resource != null) {
                return readFromURI(resource.toURI());
            }
        } catch (Exception e) {
            LOGGER.error("Internal error - can not read {}", name, e);
        }
        return null;
    }

    private CharStream readFromURI(URI uri) {
        Path path = Paths.get(uri);
        try {
            byte[] bytes = Files.readAllBytes(path);
            String result = new String(bytes, StandardCharsets.UTF_8);
            return new ANTLRInputStream(result);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read " + path, e);
        }
    }
}
