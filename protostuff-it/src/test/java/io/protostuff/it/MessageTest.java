package io.protostuff.it;

import org.junit.Assert;
import org.junit.Test;

import io.protostuff.it.message_test.MessageWithoutFields;
import io.protostuff.it.message_test.NestedMsg;
import io.protostuff.it.message_test.ParentMsg;
import io.protostuff.it.message_test.SimpleMessage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageTest {

    public static final SimpleMessage A = SimpleMessage.newBuilder()
            .setA(42)
            .setB("abra")
            .build();
    public static final SimpleMessage A_COPY = SimpleMessage.newBuilder()
            .setA(42)
            .setB("abra")
            .build();
    public static final SimpleMessage B = SimpleMessage.newBuilder()
            .setA(43)
            .setB("cadabra")
            .build();

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
        assertTrue(instance.hasNestedMsg());
        assertEquals("1", instance.getNestedMsg().getName());
        assertEquals(1, instance.getNestedRepeatedMsgCount());
        assertEquals("2", instance.getNestedRepeatedMsg(0).getName());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(A, A_COPY);
        assertNotEquals(A, B);
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(A.hashCode(), A_COPY.hashCode());
        assertNotEquals(A.hashCode(), B.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("SimpleMessage{a=42, b='abra'}", A.toString());
    }

    @Test
    public void testToString_integer_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .setA(15)
                .build();
        assertEquals("SimpleMessage{a=15}", message.toString());
    }

    @Test
    public void testToString_string_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .setB("test")
                .build();
        assertEquals("SimpleMessage{b='test'}", message.toString());
    }

    @Test
    public void testToString_MessageWithoutFields() throws Exception {
        MessageWithoutFields message = MessageWithoutFields.getDefaultInstance();
        assertEquals("MessageWithoutFields{}", message.toString());
    }
}
