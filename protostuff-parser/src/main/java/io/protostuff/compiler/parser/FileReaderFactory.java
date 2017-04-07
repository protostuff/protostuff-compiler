package io.protostuff.compiler.parser;

import java.nio.file.Path;
import java.util.List;

/**
 * Factory of {@link FileReader}'s.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileReaderFactory {

    FileReader create(List<Path> includePathList);
}
