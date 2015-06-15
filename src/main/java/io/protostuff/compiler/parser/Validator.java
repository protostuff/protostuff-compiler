package io.protostuff.compiler.parser;

/**
 * Interface for concrete proto module validator classes
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface Validator {

    void validate(ProtoContext proto);
}
