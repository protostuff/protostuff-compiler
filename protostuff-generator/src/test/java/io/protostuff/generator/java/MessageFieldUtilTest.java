package io.protostuff.generator.java;

import io.protostuff.compiler.model.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageFieldUtilTest {

    private Field f1;
    private Field f32;
    private Field f33;

    @Before
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
        Assert.assertEquals("__bitField0", MessageFieldUtil.bitFieldName(f1));
        Assert.assertEquals("__bitField0", MessageFieldUtil.bitFieldName(f32));
        Assert.assertEquals("__bitField1", MessageFieldUtil.bitFieldName(f33));
    }

    @Test
    public void testBitFieldIndex() throws Exception {
        Assert.assertEquals(0, MessageFieldUtil.bitFieldIndex(f1));
        Assert.assertEquals(31, MessageFieldUtil.bitFieldIndex(f32));
        Assert.assertEquals(0, MessageFieldUtil.bitFieldIndex(f33));
    }

    @Test
    public void testBitFieldMask() throws Exception {
        Assert.assertEquals(1, MessageFieldUtil.bitFieldMask(f1));
        Assert.assertEquals(-2147483648, MessageFieldUtil.bitFieldMask(f32));
        Assert.assertEquals(1, MessageFieldUtil.bitFieldMask(f33));
    }
}