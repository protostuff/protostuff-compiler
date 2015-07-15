package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Map;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.ScalarFieldType;
import org.junit.Assert;
import org.junit.Test;

import static io.protostuff.compiler.model.ScalarFieldType.BOOL;
import static io.protostuff.compiler.model.ScalarFieldType.FIXED32;
import static io.protostuff.compiler.model.ScalarFieldType.FIXED64;
import static io.protostuff.compiler.model.ScalarFieldType.INT32;
import static io.protostuff.compiler.model.ScalarFieldType.INT64;
import static io.protostuff.compiler.model.ScalarFieldType.SFIXED32;
import static io.protostuff.compiler.model.ScalarFieldType.SFIXED64;
import static io.protostuff.compiler.model.ScalarFieldType.SINT32;
import static io.protostuff.compiler.model.ScalarFieldType.SINT64;
import static io.protostuff.compiler.model.ScalarFieldType.STRING;
import static io.protostuff.compiler.model.ScalarFieldType.UINT32;
import static io.protostuff.compiler.model.ScalarFieldType.UINT64;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MapTest extends AbstractParserTest {

    @Test
    public void testSample() throws Exception {
        ProtoContext context = importer.importFile("protobuf_unittest/map_unittest.proto");
        Message m = context.resolve(Message.class, ".protobuf_unittest.TestMap");
        checkMap(m, "map_int32_int32", INT32, "int32", 1);
        checkMap(m, "map_int64_int64", INT64, "int64", 2);
        checkMap(m, "map_uint32_uint32", UINT32, "uint32", 3);
        checkMap(m, "map_uint64_uint64", UINT64, "uint64", 4);
        checkMap(m, "map_sint32_sint32", SINT32, "sint32", 5);
        checkMap(m, "map_sint64_sint64", SINT64, "sint64", 6);
        checkMap(m, "map_fixed32_fixed32", FIXED32, "fixed32", 7);
        checkMap(m, "map_fixed64_fixed64", FIXED64, "fixed64", 8);
        checkMap(m, "map_sfixed32_sfixed32", SFIXED32, "sfixed32", 9);
        checkMap(m, "map_sfixed64_sfixed64", SFIXED64, "sfixed64", 10);
        checkMap(m, "map_int32_float", INT32, "float", 11);
        checkMap(m, "map_int32_double", INT32, "double", 12);
        checkMap(m, "map_bool_bool", BOOL, "bool", 13);
        checkMap(m, "map_string_string", STRING, "string", 14);
        checkMap(m, "map_int32_bytes", INT32, "bytes", 15);
        checkMap(m, "map_int32_enum", INT32, ".protobuf_unittest.MapEnum", 16);
        checkMap(m, "map_int32_foreign_message", INT32, ".protobuf_unittest.ForeignMessage", 17);
    }

    private void checkMap(Message m, String mapFieldName, ScalarFieldType keyType, String valueType, int tag) {
        Field field = m.getField(mapFieldName);
        Map type = (Map) field.getType();
        Assert.assertEquals(keyType, type.getKeyType());
        Assert.assertEquals(valueType, type.getValueType().getReference());
        Assert.assertEquals(tag, field.getTag());
    }
}
