package io.protostuff.it;

import io.protostuff.ByteString;
import io.protostuff.it.scalar_test.RepeatedScalarFieldTestMsg;
import io.protostuff.it.scalar_test.ScalarFieldTestMsg;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ScalarTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void scalarDefaultValues() throws Exception {
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
        assertEquals(0, msg.getBytes().size());
    }

    @Test
    public void scalarConstructedObject() throws Exception {
        ScalarFieldTestMsg msg = ScalarFieldTestMsg.newBuilder()
                .setDouble(0.1d)
                .setFloat(0.2f)
                .setInt32(3)
                .setInt64(4L)
                .setUnsignedInt32(5)
                .setUnsignedInt64(6L)
                .setSignedInt32(7)
                .setSignedInt64(8L)
                .setFixed32(9)
                .setFixed64(10L)
                .setSignedFixed32(11)
                .setSignedFixed64(12L)
                .setBool(true)
                .setString("string")
                .setBytes(ByteString.copyFrom(new byte[]{1, 2, 3}))
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
        assertEquals(ByteString.copyFrom(new byte[]{1, 2, 3}), msg.getBytes());
    }

    @Test
    public void scalarGetters() throws Exception {
        ScalarFieldTestMsg msg = ScalarFieldTestMsg.newBuilder()
                .setDouble(0.1d)
                .setFloat(0.2f)
                .build();
        assertEquals(0.1d, msg.getDouble(), 0d);
        assertEquals(0.2f, msg.getFloat(), 0f);
    }

    @Test
    public void repeatedScalarDefaultValues() throws Exception {
        RepeatedScalarFieldTestMsg msg = RepeatedScalarFieldTestMsg.newBuilder().build();
        assertEquals(0, msg.getDoubleCount());
        assertEquals(Collections.emptyList(), msg.getDoubleList());
    }

    @Test
    public void repeatedScalarConstructedObject_usingSetter() throws Exception {
        RepeatedScalarFieldTestMsg msg = RepeatedScalarFieldTestMsg.newBuilder()
                .addAllInt32(Arrays.asList(1, 2, 3))
                .build();
        assertEquals(3, msg.getInt32Count());
        assertEquals(1, msg.getInt32(0));
        assertEquals(2, msg.getInt32(1));
        assertEquals(3, msg.getInt32(2));
    }

    @Test
    public void repeatedScalarConstructedObject_usingAdder() throws Exception {
        RepeatedScalarFieldTestMsg msg = RepeatedScalarFieldTestMsg.newBuilder()
                .addInt32(1)
                .addInt32(2)
                .addInt32(3)
                .build();
        assertEquals(3, msg.getInt32Count());
        assertEquals(1, msg.getInt32(0));
        assertEquals(2, msg.getInt32(1));
        assertEquals(3, msg.getInt32(2));
    }

    @Test
    public void repeatedScalarConstructedObject_usingSetAtIndex() throws Exception {
        RepeatedScalarFieldTestMsg msg = RepeatedScalarFieldTestMsg.newBuilder()
                .addInt32(0)
                .addInt32(0)
                .addInt32(0)
                .setInt32(0, 1)
                .setInt32(1, 2)
                .setInt32(2, 3)
                .build();
        assertEquals(3, msg.getInt32Count());
        assertEquals(1, msg.getInt32(0));
        assertEquals(2, msg.getInt32(1));
        assertEquals(3, msg.getInt32(2));
    }

    @Test
    public void repeatedScalarConstructedObject_usingAddAll() throws Exception {
        Iterable<Integer> numbers = Arrays.asList(1,2,3);
        RepeatedScalarFieldTestMsg msg = RepeatedScalarFieldTestMsg.newBuilder()
                .addAllInt32(numbers)
                .build();
        assertEquals(3, msg.getInt32Count());
        assertEquals(1, msg.getInt32(0));
        assertEquals(2, msg.getInt32(1));
        assertEquals(3, msg.getInt32(2));
    }

    @Test
    public void repeatedScalarConstructedObject_ListIsImmutable() throws Exception {
        RepeatedScalarFieldTestMsg msg = RepeatedScalarFieldTestMsg.newBuilder()
                .addInt32(1)
                .addInt32(2)
                .addInt32(3)
                .build();
        thrown.expect(NullPointerException.class);
        msg.getInt32List().add(4);
    }

    @Test
    public void repeatedScalarConstructObject_addAllWithNull() throws Exception {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(null);
        numbers.add(3);
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Cannot set RepeatedScalarFieldTestMsg#int32 to null");
        RepeatedScalarFieldTestMsg.newBuilder()
                .addAllInt32(numbers)
                .build();
    }
}
