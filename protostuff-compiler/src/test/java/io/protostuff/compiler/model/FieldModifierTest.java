package io.protostuff.compiler.model;

import org.junit.Assert;
import org.junit.Test;

import static io.protostuff.compiler.model.FieldModifier.OPTIONAL;
import static io.protostuff.compiler.model.FieldModifier.REPEATED;
import static io.protostuff.compiler.model.FieldModifier.REQUIRED;

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