package io.protostuff.it;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class GeneratedCodeTest {

    @Test
    public void primitiveDefaultValues() throws Exception {
        PrimitiveFieldTestMsg msg = PrimitiveFieldTestMsg.newBuilder().build();
        assertEquals(0d, msg.getDouble(), 0d);
        assertEquals(0f, msg.getFloat(), 0f);
        assertEquals(0, msg.getInt32());
        assertEquals(0L, msg.getInt64());
        assertEquals(0, msg.getUnsignedInt32());
        assertEquals(0L, msg.getUnsignedInt32());
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
}
