package io.protostuff.compiler.model;

import java.io.Serializable;

/**
 * Location of tree node, represented by file name
 * and line number.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class SourceCodeLocation implements Serializable {

    public static final SourceCodeLocation UNKNOWN = new SourceCodeLocation() {
        @Override
        public String toString() {
            return "unknown";
        }
    };
    private static final long serialVersionUID = 0L;
    private final String file;
    private final int line;

    private SourceCodeLocation() {
        file = "unknown";
        line = 0;
    }

    public SourceCodeLocation(String file, int line) {
        this.file = file;
        this.line = line;
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return file + ":" + line;
    }
}
