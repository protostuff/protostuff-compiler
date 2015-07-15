package io.protostuff.compiler.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class StErrorListener implements STErrorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StErrorListener.class);

    @Override
    public void compileTimeError(STMessage stMessage) {
        LOGGER.error("Compile time error: {}", stMessage);
    }

    @Override
    public void runTimeError(STMessage stMessage) {
        LOGGER.error("Runtime error: {}", stMessage);
    }

    @Override
    public void IOError(STMessage stMessage) {
        LOGGER.error("IO error: {}", stMessage);
    }

    @Override
    public void internalError(STMessage stMessage) {
        LOGGER.error("Internal error: {}", stMessage);
    }
}
