package io.protostuff.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Kostiantyn Shchepanovskyi
 */
public class FormatterTest {

    @Test
    public void testToUpperCase() throws Exception {
        assertEquals("SOME_FOO", Formatter.toUpperCase("some_foo"));
    }

    @Test
    public void testToLowerCase() throws Exception {
        assertEquals("some_foo", Formatter.toLowerCase("SOME_FOO"));
    }

    @Test
    public void testToCamelCase() throws Exception {
        assertEquals("someFoo", Formatter.toCamelCase("some_foo"));
        assertEquals("someFoo", Formatter.toCamelCase("SomeFoo"));
    }

    @Test
    public void testToUnderscoreCase() throws Exception {
        assertEquals("some_foo", Formatter.toUnderscoreCase("someFoo"));
        assertEquals("some_foo", Formatter.toUnderscoreCase("SomeFoo"));
    }

    @Test
    public void testToPascalCase() throws Exception {
        assertEquals("SomeFoo", Formatter.toPascalCase("some_foo"));
        assertEquals("SomeFoo", Formatter.toPascalCase("someFoo"));
    }
}