package io.protostuff.it;

import org.junit.Assert;
import org.junit.Test;

import io.protostuff.it.message_test.NestedMsg;
import io.protostuff.it.message_test.ParentMsg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageTest {

    @Test
    public void defaultInstance() throws Exception {
        ParentMsg instance = ParentMsg.getDefaultInstance();
        assertSame(NestedMsg.getDefaultInstance(), instance.getNestedMsg());
        assertFalse(instance.hasNestedMsg());
        assertEquals(0, instance.getNestedRepeatedMsgCount());
    }

    @Test
    public void createdInstance() throws Exception {
        ParentMsg instance = ParentMsg.newBuilder()
                .setNestedMsg(NestedMsg.newBuilder()
                        .setName("1")
                        .build())
                .addNestedRepeatedMsg(NestedMsg.newBuilder()
                        .setName("2")
                        .build())
                .build();
        Assert.assertTrue(instance.hasNestedMsg());
        Assert.assertEquals("1", instance.getNestedMsg().getName());
        Assert.assertEquals(1, instance.getNestedRepeatedMsgCount());
        Assert.assertEquals("2", instance.getNestedRepeatedMsg(0).getName());
    }
}
