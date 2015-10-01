package io.protostuff.it;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class GeneratedCodeTest {

    @Test
    public void primitiveDefaultValues() throws Exception {
        ScalarFieldTestMsg msg = ScalarFieldTestMsg.newBuilder().build();
        assertEquals(0d, msg.getDouble(), 0d);
        assertEquals(0f, msg.getFloat(), 0f);
        assertEquals(0, msg.getInt32());
        assertEquals(0L, msg.getInt64());
        assertEquals(0, msg.getUnsignedInt32());
        assertEquals(0L, msg.getUnsignedInt64());
        assertEquals(0, msg.getSignedInt32());
        assertEquals(0L, msg.getSignedInt64());
        assertEquals(0, msg.getFixed32());
        assertEquals(0L, msg.getFixed64());
        assertEquals(0, msg.getSignedFixed32());
        assertEquals(0L, msg.getSignedFixed64());
        assertEquals(false, msg.getBool());
        assertEquals("", msg.getString());
        assertEquals(0, msg.getBytes().length);
    }

    @Test
    public void primitiveConstructedObject() throws Exception {
        ScalarFieldTestMsg msg = ScalarFieldTestMsg.newBuilder()
                .setDouble(0.1d)
                .setFloat(0.2f)
                .setInt32(3)
                .setInt64(4)
                .setUnsignedInt32(5)
                .setUnsignedInt64(6)
                .setSignedInt32(7)
                .setSignedInt64(8)
                .setFixed32(9)
                .setFixed64(10)
                .setSignedFixed32(11)
                .setSignedFixed64(12)
                .setBool(true)
                .setString("string")
                .setBytes(new byte[] {1,2,3})
                .build();
        assertEquals(0.1d, msg.getDouble(), 0d);
        assertEquals(0.2f, msg.getFloat(), 0f);
        assertEquals(3, msg.getInt32());
        assertEquals(4L, msg.getInt64());
        assertEquals(5, msg.getUnsignedInt32());
        assertEquals(6L, msg.getUnsignedInt64());
        assertEquals(7, msg.getSignedInt32());
        assertEquals(8L, msg.getSignedInt64());
        assertEquals(9, msg.getFixed32());
        assertEquals(10L, msg.getFixed64());
        assertEquals(11, msg.getSignedFixed32());
        assertEquals(12L, msg.getSignedFixed64());
        assertEquals(true, msg.getBool());
        assertEquals("string", msg.getString());
        assertArrayEquals(new byte[] {1,2,3}, msg.getBytes());
    }
}
