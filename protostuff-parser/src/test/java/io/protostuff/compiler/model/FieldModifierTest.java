package io.protostuff.compiler.model;

import org.junit.jupiter.api.Test;

import static io.protostuff.compiler.model.FieldModifier.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class FieldModifierTest {

    @Test
    public void testToString() throws Exception {
        assertEquals("optional", OPTIONAL.toString());
        assertEquals("required", REQUIRED.toString());
        assertEquals("repeated", REPEATED.toString());
    }
}