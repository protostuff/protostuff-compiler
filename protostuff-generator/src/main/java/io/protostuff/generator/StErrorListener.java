package io.protostuff.generator;

import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

/**
 * Error listener for StringTemplate engine.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class StErrorListener implements STErrorListener {

    @Override
    public void compileTimeError(STMessage stMessage) {
        throw new GeneratorException(stMessage.toString());
    }

    @Override
    public void runTimeError(STMessage stMessage) {
        throw new GeneratorException(stMessage.toString());
    }

    @Override
    public void IOError(STMessage stMessage) {
        throw new GeneratorException(stMessage.toString());
    }

    @Override
    public void internalError(STMessage stMessage) {
        throw new GeneratorException(stMessage.toString());
    }
}
