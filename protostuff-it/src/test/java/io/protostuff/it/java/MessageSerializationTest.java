package io.protostuff.it.java;

import io.protostuff.*;
import io.protostuff.it.enum_test.NestedEnum;
import io.protostuff.it.enum_test.ParentEnumMsg;
import io.protostuff.it.message_test.*;
import io.protostuff.it.scalar_test.ScalarFieldTestMsg;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private static final String REPEATED_JSON = "{\"repeatedInt32\":[42,43],\"repeatedString\":[\"line1\",\"line2\"]}";
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
        assertEquals(SCALARS_JSON, json);
    }

    @Test
    public void scalars_deserialize() throws Exception {
        SimpleMessage result = SCHEMA.newMessage();
        JsonIOUtil.mergeFrom(SCALARS_JSON.getBytes(), result, SCHEMA, false);
        assertEquals(SCALARS_MESSAGE, result);
    }

    @Test
    public void repeated_serialize() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonIOUtil.writeTo(stream, REPEATED_MESSAGE, SCHEMA, false);
        String json = new String(stream.toByteArray());
        assertEquals(REPEATED_JSON, json);
    }

    @Test
    public void repeated_deserialize() throws Exception {
        SimpleMessage result = SCHEMA.newMessage();
        JsonIOUtil.mergeFrom(REPEATED_JSON.getBytes(), result, SCHEMA, false);
        assertEquals(REPEATED_MESSAGE, result);
    }

    @Test
    public void nested_serialize() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonIOUtil.writeTo(stream, NESTED_MESSAGE, SCHEMA, false);
        String json = new String(stream.toByteArray());
        assertEquals(NESTED_JSON, json);
    }

    @Test
    public void nested_deserialize() throws Exception {
        SimpleMessage result = SCHEMA.newMessage();
        JsonIOUtil.mergeFrom(NESTED_JSON.getBytes(), result, SCHEMA, false);
        assertEquals(NESTED_MESSAGE, result);
    }

    @Test
    public void map_serialization_deserialization() throws Exception {
        SimpleMessage simpleMessage = SimpleMessage.newBuilder()
                .setInt32(5)
                .build();
        TestMap container = TestMap.newBuilder()
                .putMapBoolBool(true, true)
                .putMapBoolBool(false, false)
                .putMapInt32Int32(43, 42)
                .putMapInt32Int32(1, 0)
                .putMapStringSimpleMessage("key", simpleMessage)
                .build();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ProtobufIOUtil.writeTo(stream, container, TestMap.getSchema(), LinkedBuffer.allocate());
        byte[] bytes = stream.toByteArray();
        TestMap newInstance = TestMap.getSchema().newMessage();
        ProtobufIOUtil.mergeFrom(bytes, newInstance, TestMap.getSchema());
        assertEquals(true, newInstance.getMapBoolBool(true));
        assertEquals(false, newInstance.getMapBoolBool(false));
        assertEquals(42, newInstance.getMapInt32Int32(43).intValue());
        assertEquals(0, newInstance.getMapInt32Int32(1).intValue());
        assertEquals(simpleMessage, newInstance.getMapStringSimpleMessage("key"));
        assertEquals(container, newInstance);
    }

    @Test
    public void testOneof_serialization_deserialization() throws Exception {
        TestOneof a = TestOneof.newBuilder()
                .setFooString("abra")
                .setMapEnum(MapEnum.MAP_ENUM_BAZ)
                .build();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ProtobufIOUtil.writeTo(stream, a, TestOneof.getSchema(), LinkedBuffer.allocate());
        byte[] bytes = stream.toByteArray();
        TestOneof newInstance = TestOneof.getSchema().newMessage();
        ProtobufIOUtil.mergeFrom(bytes, newInstance, TestOneof.getSchema());
        assertEquals(a, newInstance);
    }

    @Test
    public void testEnum_serialization_deserialization() throws Exception {
        ParentEnumMsg a = ParentEnumMsg.newBuilder()
                .setFirst(NestedEnum.HUNDRED)
                .addNestedRepeatedEnum(NestedEnum.FIRST)
                .addNestedRepeatedEnum(NestedEnum.FIRST)
                .addNestedRepeatedEnum(NestedEnum.SECOND)
                .build();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ProtobufIOUtil.writeTo(stream, a, ParentEnumMsg.getSchema(), LinkedBuffer.allocate());
        byte[] bytes = stream.toByteArray();
        ParentEnumMsg newInstance = ParentEnumMsg.getSchema().newMessage();
        ProtobufIOUtil.mergeFrom(bytes, newInstance, ParentEnumMsg.getSchema());
        assertEquals(a, newInstance);
        assertEquals(NestedEnum.HUNDRED, newInstance.getFirst());
        assertEquals(NestedEnum.FIRST, newInstance.getNestedRepeatedEnum(0));
        assertEquals(NestedEnum.FIRST, newInstance.getNestedRepeatedEnum(1));
        assertEquals(NestedEnum.SECOND, newInstance.getNestedRepeatedEnum(2));
    }

    @Test
    public void JSON_fieldNameIsPreservedForJavaReservedKeyword() throws Exception {
        ScalarFieldTestMsg msg = ScalarFieldTestMsg.newBuilder()
                .setDouble(0.1d)
                .build();
        Schema<ScalarFieldTestMsg> schema =  msg.cachedSchema();
        String json = serialize(msg);
        assertEquals("{\"double\":0.1}", json);
        ScalarFieldTestMsg deserialized = deserialize(json, schema);
        assertEquals(msg, deserialized);
    }

    @Test
    public void oneofSerialization() throws Exception {
        TestOneof msg = TestOneof.newBuilder()
                .setFooString("foo")
                .build();
        String json = serialize(msg);
        assertEquals("{\"fooString\":\"foo\"}", json);
        TestOneof deserialzied = deserialize(json, TestOneof.getSchema());
        assertEquals(msg, deserialzied);
    }

    @Test
    public void testDefaultInstanceIsNotUsedForDeserialization() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .setMessage(TestMessage.newBuilder()
                        .setA(2)
                        .build())
                .build();
        byte[] data = ProtobufIOUtil.toByteArray(message, SCHEMA, LinkedBuffer.allocate());
        // used to catch one special defect when data is merged to a default instance
        // due to a defect in code generator
        ProtobufIOUtil.mergeFrom(data, SCHEMA.newMessage(), SCHEMA);
        ProtobufIOUtil.mergeFrom(data, SCHEMA.newMessage(), SCHEMA);
    }

    private <T> T deserialize(String json, Schema<T> schema) throws java.io.IOException {
        T result = schema.newMessage();
        JsonIOUtil.mergeFrom(json.getBytes(), result, schema, false);
        return result;
    }

    @SuppressWarnings("unchecked")
    private String serialize(Message msg) throws java.io.IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonIOUtil.writeTo(stream, msg, msg.cachedSchema(), false);
        return new String(stream.toByteArray());
    }
}
