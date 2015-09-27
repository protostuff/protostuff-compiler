package io.protostuff.generator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class FormatterTest {

    @Test
    public void testToUpperCase() throws Exception {
        Assert.assertEquals("SOME_FOO", Formatter.toUpperCase("some_foo"));
    }

    @Test
    public void testToLowerCase() throws Exception {
        Assert.assertEquals("some_foo", Formatter.toLowerCase("SOME_FOO"));
    }

    @Test
    public void testToCamelCase() throws Exception {
        Assert.assertEquals("someFoo", Formatter.toCamelCase("some_foo"));
        Assert.assertEquals("someFoo", Formatter.toCamelCase("SomeFoo"));
    }

    @Test
    public void testToUnderscoreCase() throws Exception {
        Assert.assertEquals("some_foo", Formatter.toUnderscoreCase("someFoo"));
        Assert.assertEquals("some_foo", Formatter.toUnderscoreCase("SomeFoo"));
    }

    @Test
    public void testToPascalCase() throws Exception {
        Assert.assertEquals("SomeFoo", Formatter.toPascalCase("some_foo"));
        Assert.assertEquals("SomeFoo", Formatter.toPascalCase("someFoo"));
    }
}