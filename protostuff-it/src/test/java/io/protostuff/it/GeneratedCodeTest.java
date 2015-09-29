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
    }
}
