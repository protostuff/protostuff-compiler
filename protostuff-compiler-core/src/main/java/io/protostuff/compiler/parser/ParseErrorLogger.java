package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ParseErrorLogger extends BaseErrorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseErrorLogger.class);

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        LOGGER.error("Can not parse line {}:{} - {}", line, charPositionInLine, msg);
    }

}
