package io.protostuff.compiler.parser;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ParserException extends RuntimeException {

    public ParserException(String format, Object... args) {
        super(String.format(format, args));
    }

    public ParserException(String format, Throwable cause, Object... args) {
        super(String.format(format, args), cause);
    }
}
