package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class TestUtils {

    public static final ErrorListener ERROR_LISTENER = new ErrorListener();

    public static class ErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            throw new IllegalStateException("Can not parse line " + line + " position " + charPositionInLine + ": " + msg, e);
        }
    }
}
