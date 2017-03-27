package io.protostuff.compiler.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class ScalarFieldTypeTest {

    @Test
    void int32() {
        assertTrue(ScalarFieldType.INT32.isScalar());
        assertFalse(ScalarFieldType.INT32.isBytes());
        assertFalse(ScalarFieldType.INT32.isString());
        assertFalse(ScalarFieldType.INT32.isEnum());
        assertFalse(ScalarFieldType.INT32.isMap());
        assertFalse(ScalarFieldType.INT32.isMessage());
    }

    @Test
    void string() {
        assertTrue(ScalarFieldType.STRING.isScalar());
        assertFalse(ScalarFieldType.STRING.isBytes());
        assertTrue(ScalarFieldType.STRING.isString());
        assertFalse(ScalarFieldType.STRING.isEnum());
        assertFalse(ScalarFieldType.STRING.isMap());
        assertFalse(ScalarFieldType.STRING.isMessage());
    }

    @Test
    void bytes() {
        assertTrue(ScalarFieldType.BYTES.isScalar());
        assertTrue(ScalarFieldType.BYTES.isBytes());
        assertFalse(ScalarFieldType.BYTES.isString());
        assertFalse(ScalarFieldType.BYTES.isEnum());
        assertFalse(ScalarFieldType.BYTES.isMap());
        assertFalse(ScalarFieldType.BYTES.isMessage());
    }
}