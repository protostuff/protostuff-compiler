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

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("optional", OPTIONAL.toString());
        Assert.assertEquals("required", REQUIRED.toString());
        Assert.assertEquals("repeated", REPEATED.toString());
    }
}