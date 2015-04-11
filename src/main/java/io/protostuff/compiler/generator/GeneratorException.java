package io.protostuff.compiler.generator;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class GeneratorException extends RuntimeException {

    public GeneratorException(String format, Object... args) {
        super(String.format(format, args));
    }
}
