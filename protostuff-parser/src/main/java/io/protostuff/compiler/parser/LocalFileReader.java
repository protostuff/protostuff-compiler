package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Load file from local filesystem.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class LocalFileReader implements FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileReader.class);

    private final List<File> pathList;

    public LocalFileReader(File... paths) {
        this.pathList = checkDirectories(Arrays.asList(paths));
    }

    public LocalFileReader(List<File> paths) {
        this.pathList = checkDirectories(paths);
    }

    /**
     * Check that all elements in the list exist and are directories.
     * Log warning for each element that is not directory.
     */
    private List<File> checkDirectories(List<File> pathList) {
        List<File> result = new ArrayList<File>();
        for (File path : pathList) {
            if (!path.exists()) {
                LOGGER.warn("'{}' does not exist", path);
            } else if (!path.isDirectory()) {
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
        for (File prefix : pathList) {
            try {
                String pathString = resolveRelativeFile(prefix, name);
                RandomAccessFile path = new RandomAccessFile(pathString, "r");
                byte[] bytes = new byte[(int) path.length()];
                path.readFully(bytes);
                path.close();
                String result = new String(bytes, "UTF-8");
                return new ANTLRInputStream(result);
            } catch (FileNotFoundException e) {
                // ignore
            } catch (IOException e) {
                LOGGER.debug("Could not read/resolve {} + {}", prefix, name);
            }

        }
        return null;
    }

    public static String resolveRelativeFile(File parent, String file) throws IOException {
        return parent.getCanonicalPath() + "/" + file;
    }
}
