package io.protostuff.compiler.parser;

import java.io.File;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FileReaderFactory {

    FileReader create(List<File> includePathList);
}
