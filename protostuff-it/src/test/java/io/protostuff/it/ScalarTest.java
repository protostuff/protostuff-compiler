package io.protostuff.it;

import io.protostuff.ByteString;
import io.protostuff.it.scalar_test.RepeatedScalarFieldTestMsg;
import io.protostuff.it.scalar_test.ScalarFieldTestMsg;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

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
        assertEquals(0, (int) msg.getInt32());
        assertEquals(0L, (long) msg.getInt64());
        assertEquals(0, (int) msg.getUnsignedInt32());
        assertEquals(0L, (long) msg.getUnsignedInt64());
        assertEquals(0, (int) msg.getSignedInt32());
        assertEquals(0L, (long) msg.getSignedInt64());
        assertEquals(0, (int) msg.getFixed32());
        assertEquals(0L, (long) msg.getFixed64());
        assertEquals(0, (int) msg.getSignedFixed32());
        assertEquals(0L, (long) msg.getSignedFixed64());
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
        assertEquals(3, (int) msg.getInt32());
        assertEquals(4L, (long) msg.getInt64());
        assertEquals(5, (int) msg.getUnsignedInt32());
        assertEquals(6L, (long) msg.getUnsignedInt64());
        assertEquals(7, (int) msg.getSignedInt32());
        assertEquals(8L, (long) msg.getSignedInt64());
        assertEquals(9, (int) msg.getFixed32());
        assertEquals(10L, (long) msg.getFixed64());
        assertEquals(11, (int) msg.getSignedFixed32());
        assertEquals(12L, (long) msg.getSignedFixed64());
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
        assertEquals(1, (int) msg.getInt32(0));
        assertEquals(2, (int) msg.getInt32(1));
        assertEquals(3, (int) msg.getInt32(2));
    }

    @Test
    public void repeatedScalarConstructedObject_usingAdder() throws Exception {
        RepeatedScalarFieldTestMsg msg = RepeatedScalarFieldTestMsg.newBuilder()
                .addInt32(1)
                .addInt32(2)
                .addInt32(3)
                .build();
        assertEquals(3, msg.getInt32Count());
        assertEquals(1, (int) msg.getInt32(0));
        assertEquals(2, (int) msg.getInt32(1));
        assertEquals(3, (int) msg.getInt32(2));
    }

    @Test
    public void repeatedScalarConstructedObject_ListIsImmutable() throws Exception {
        RepeatedScalarFieldTestMsg msg = RepeatedScalarFieldTestMsg.newBuilder()
                .addInt32(1)
                .addInt32(2)
                .addInt32(3)
                .build();
        thrown.expect(UnsupportedOperationException.class);
        msg.getInt32List().add(4);
    }
}
