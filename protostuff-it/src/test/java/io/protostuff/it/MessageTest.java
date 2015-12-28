package io.protostuff.it;

import io.protostuff.it.message_test.MessageWithoutFields;
import io.protostuff.it.message_test.NestedMsg;
import io.protostuff.it.message_test.ParentMsg;
import io.protostuff.it.message_test.SimpleMessage;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageTest {

    public static final SimpleMessage A = SimpleMessage.newBuilder()
            .setInt32(42)
            .setString("abra")
            .build();
    public static final SimpleMessage A_COPY = SimpleMessage.newBuilder()
            .setInt32(42)
            .setString("abra")
            .build();
    public static final SimpleMessage B = SimpleMessage.newBuilder()
            .setInt32(43)
            .setString("cadabra")
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
        assertEquals("SimpleMessage{int32=42, string=abra}", A.toString());
    }

    @Test
    public void testToString_integer_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .setInt32(15)
                .build();
        assertEquals("SimpleMessage{int32=15}", message.toString());
    }

    @Test
    public void testToString_string_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .setString("test")
                .build();
        assertEquals("SimpleMessage{string=test}", message.toString());
    }

    @Test
    public void testToString_repeated_string_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .addRepeatedString("test1")
                .addRepeatedString("test2")
                .build();
        assertEquals("SimpleMessage{repeated_string=[test1, test2]}", message.toString());
    }

    @Test
    public void testToString_repeated_int32_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .addRepeatedInt32(41)
                .addRepeatedInt32(42)
                .build();
        assertEquals("SimpleMessage{repeated_int32=[41, 42]}", message.toString());
    }

    @Test
    public void testToString_MessageWithoutFields() throws Exception {
        MessageWithoutFields message = MessageWithoutFields.getDefaultInstance();
        assertEquals("MessageWithoutFields{}", message.toString());
    }
}
