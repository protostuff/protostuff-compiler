package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Element;
import io.protostuff.compiler.model.SourceCodeLocation;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ParserException extends RuntimeException {

    private final SourceCodeLocation sourceCodeLocation;

    public ParserException(Element sourceElement, String format, Object... args) {
        super(String.format(format, args) + " [" + sourceElement.getSourceCodeLocation() + "]");
        this.sourceCodeLocation = sourceElement.getSourceCodeLocation();
    }

    public ParserException(String format, Object... args) {
        super(String.format(format, args));
        sourceCodeLocation = SourceCodeLocation.UNKNOWN;
    }

    public SourceCodeLocation getSourceCodeLocation() {
        return sourceCodeLocation;
    }
}
