package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Load file from local filesystem.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class LocalFileReader implements FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileReader.class);

    private final List<Path> pathList;

    public LocalFileReader(Path... paths) {
        this.pathList = checkDirectories(Arrays.asList(paths));
    }

    public LocalFileReader(List<Path> paths) {
        this.pathList = checkDirectories(paths);
    }

    /**
     * Check that all elements in the list exist and are directories.
     * Log warning for each element that is not directory.
     */
    private List<Path> checkDirectories(List<Path> pathList) {
        List<Path> result = new ArrayList<>();
        for (Path path : pathList) {
            if (!Files.exists(path)) {
                LOGGER.warn("'{}' does not exist", path);
            } else if (!Files.isDirectory(path)) {
                LOGGER.warn("'{}' is not directory", path);
            }
            // todo: should we use not existing paths? this behavior is copied from 'protoc' - it just shows warning
            result.add(path);
        }
        return result;
    }

    @Nullable
    @Override
    public CharStream read(String name) {
        for (Path prefix : pathList) {
            Path path = prefix.resolve(name);
            if (Files.isRegularFile(path)) {
                try {
                    byte[] bytes = Files.readAllBytes(path);
                    String result = new String(bytes, StandardCharsets.UTF_8);
                    return new ANTLRInputStream(result);
                } catch (IOException e) {
                    LOGGER.trace("Could not read {}", path, e);
                }
            }
        }
        return null;
    }
}
