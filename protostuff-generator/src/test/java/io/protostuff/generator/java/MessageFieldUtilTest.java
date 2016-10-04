package io.protostuff.generator.java;

import io.protostuff.compiler.model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageFieldUtilTest {

    private Field f1;
    private Field f32;
    private Field f33;

    @BeforeEach
    public void setUp() throws Exception {
        f1 = new Field(null);
        f1.setIndex(1);
        f32 = new Field(null);
        f32.setIndex(32);
        f33 = new Field(null);
        f33.setIndex(33);
    }

    @Test
    public void testBitFieldName() throws Exception {
        assertEquals("__bitField0", MessageFieldUtil.bitFieldName(f1));
        assertEquals("__bitField0", MessageFieldUtil.bitFieldName(f32));
        assertEquals("__bitField1", MessageFieldUtil.bitFieldName(f33));
    }

    @Test
    public void testBitFieldIndex() throws Exception {
        assertEquals(0, MessageFieldUtil.bitFieldIndex(f1));
        assertEquals(31, MessageFieldUtil.bitFieldIndex(f32));
        assertEquals(0, MessageFieldUtil.bitFieldIndex(f33));
    }

    @Test
    public void testBitFieldMask() throws Exception {
        assertEquals(1, MessageFieldUtil.bitFieldMask(f1));
        assertEquals(-2147483648, MessageFieldUtil.bitFieldMask(f32));
        assertEquals(1, MessageFieldUtil.bitFieldMask(f33));
    }
}