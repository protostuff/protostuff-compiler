package io.protostuff.compiler.parser;

import org.junit.jupiter.api.Test;

import static io.protostuff.compiler.parser.Util.removeFirstAndLastChar;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class UtilTest {

    @Test
    public void testRemoveQuotes_expected() throws Exception {
        assertEquals("abc", removeFirstAndLastChar("\"abc\""));
    }

}