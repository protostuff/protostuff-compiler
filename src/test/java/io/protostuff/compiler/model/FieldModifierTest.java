package io.protostuff.compiler.model;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static io.protostuff.compiler.model.FieldModifier.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class FieldModifierTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testParse_optional() throws Exception {
        assertEquals(OPTIONAL, FieldModifier.parse("optional"));
    }

    @Test
    public void testParse_required() throws Exception {
        assertEquals(REQUIRED, FieldModifier.parse("required"));
    }

    @Test
    public void testParse_repeated() throws Exception {
        assertEquals(REPEATED, FieldModifier.parse("repeated"));
    }

    @Test
    public void testParse_unknown() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("unknown");
        FieldModifier.parse("unknown");
    }

    @Test
    public void testParse_null() throws Exception {
        thrown.expect(NullPointerException.class);
        FieldModifier.parse(null);
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("optional", OPTIONAL.toString());
        Assert.assertEquals("required", REQUIRED.toString());
        Assert.assertEquals("repeated", REPEATED.toString());
    }
}