package io.protostuff.compiler.parser;

/**
 * Proto post processors are invoked on a proto context after parser.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface ProtoPostProcessor {

    void process(ProtoContext context);
}
