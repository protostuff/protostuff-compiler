package io.protostuff.compiler.generator;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class GeneratorException extends RuntimeException {

    public GeneratorException(String format, Object... args) {
        super(String.format(format, args));
    }

    public GeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneratorException(String format, Throwable cause, Object... args) {
        super(String.format(format, args), cause);
    }
}
