package io.protostuff.compiler.parser;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileReaderFactory {

    FileReader create(List<Path> includePathList);
}
