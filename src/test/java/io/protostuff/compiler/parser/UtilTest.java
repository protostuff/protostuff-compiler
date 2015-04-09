package io.protostuff.compiler.parser;

import org.junit.Test;

import static io.protostuff.compiler.parser.Util.removeQuotes;
import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class UtilTest {

    @Test
    public void testRemoveQuotes_expected() throws Exception {
        assertEquals("abc", removeQuotes("\"abc\""));
    }

    @Test
    public void testRemoveQuotes_no_quotes() throws Exception {
        // do nothing
        assertEquals("abc", removeQuotes("abc"));
    }

    @Test
    public void testRemoveQuotes_single_quote() throws Exception {
        // do nothing
        assertEquals("abc\"", removeQuotes("abc\""));
    }

    @Test
    public void testRemoveQuotes_in_the_text() throws Exception {
        // do nothing
        assertEquals("b", removeQuotes("a\"b\"c"));
    }
}