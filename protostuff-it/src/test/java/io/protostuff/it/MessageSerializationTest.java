package io.protostuff.it;

import io.protostuff.JsonIOUtil;
import io.protostuff.Schema;
import io.protostuff.it.message_test.SimpleMessage;
import io.protostuff.it.message_test.TestMessage;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageSerializationTest {

    private static final Schema<SimpleMessage> SCHEMA = SimpleMessage.getSchema();

    private static final String SCALARS_JSON = "{\"int32\":42,\"string\":\"xxx\"}";
    private static final SimpleMessage SCALARS_MESSAGE =SimpleMessage.newBuilder()
            .setInt32(42)
            .setString("xxx")
            .build();
    private static final String REPEATED_JSON = "{\"repeated_int32\":[42,43],\"repeated_string\":[\"line1\",\"line2\"]}";
    private static final SimpleMessage REPEATED_MESSAGE =SimpleMessage.newBuilder()
            .addRepeatedInt32(42)
            .addRepeatedInt32(43)
            .addRepeatedString("line1")
            .addRepeatedString("line2")
            .build();
    private static final String NESTED_JSON = "{\"message\":{\"a\":3}}";
    private static final SimpleMessage NESTED_MESSAGE =SimpleMessage.newBuilder()
            .setMessage(TestMessage.newBuilder()
                    .setA(3)
                    .build())
            .build();

    @Test
    public void scalars_serialize() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonIOUtil.writeTo(stream, SCALARS_MESSAGE, SCHEMA, false);
        String json = new String(stream.toByteArray());
        Assert.assertEquals(SCALARS_JSON, json);
    }

    @Test
    public void scalars_deserialize() throws Exception {
        SimpleMessage result = SCHEMA.newMessage();
        JsonIOUtil.mergeFrom(SCALARS_JSON.getBytes(), result, SCHEMA, false);
        Assert.assertEquals(SCALARS_MESSAGE, result);
    }

    @Test
    public void repeated_serialize() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonIOUtil.writeTo(stream, REPEATED_MESSAGE, SCHEMA, false);
        String json = new String(stream.toByteArray());
        Assert.assertEquals(REPEATED_JSON, json);
    }

    @Test
    public void repeated_deserialize() throws Exception {
        SimpleMessage result = SCHEMA.newMessage();
        JsonIOUtil.mergeFrom(REPEATED_JSON.getBytes(), result, SCHEMA, false);
        Assert.assertEquals(REPEATED_MESSAGE, result);
    }

    @Test
    public void nested_serialize() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonIOUtil.writeTo(stream, NESTED_MESSAGE, SCHEMA, false);
        String json = new String(stream.toByteArray());
        Assert.assertEquals(NESTED_JSON, json);
    }

    @Test
    public void nested_deserialize() throws Exception {
        SimpleMessage result = SCHEMA.newMessage();
        JsonIOUtil.mergeFrom(NESTED_JSON.getBytes(), result, SCHEMA, false);
        Assert.assertEquals(NESTED_MESSAGE, result);
    }
}
