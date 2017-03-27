package io.protostuff.generator.html.json.message;

import io.protostuff.generator.html.json.ImmutableUsageItem;
import io.protostuff.generator.html.json.UsageType;
import io.protostuff.generator.html.json.index.NodeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class JsonMessageGeneratorTest extends AbstractJsonGeneratorTest {


    @Test
    void testSample() {
        compile(messageGenerator, "protostuff_unittest/messages_sample.proto");
        Assertions.assertEquals(4, json.size());
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("A")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("a")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.REPEATED)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(0));
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("B")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A.B")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("b")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(1));
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("C")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A.B.C")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("c")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(2));
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("D")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A.B.C.D")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("d")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(3));
    }


    @Test
    void fieldModifiers() {
        compile(messageGenerator, "protostuff_unittest/field_modifiers.proto");
        Assertions.assertEquals(1, json.size());
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("A")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("optional")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .addFields(ImmutableMessageField.builder()
                        .name("required")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.REQUIRED)
                        .tag(2)
                        .map(false)
                        .description("")
                        .build())
                .addFields(ImmutableMessageField.builder()
                        .name("repeated")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.REPEATED)
                        .tag(3)
                        .map(false)
                        .description("")
                        .build())
                .build(), json.get(0));
    }

    @Test
    void map() {
        compile(messageGenerator, "protostuff_unittest/map.proto");
        Assertions.assertEquals(2, json.size());
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("A")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("map")
                        .typeId("protostuff_unittest.A.map_entry")
                        .modifier(MessageFieldModifier.REPEATED)
                        .tag(1)
                        .map(true)
                        .mapKeyTypeId("int32")
                        .mapValueTypeId("int32")
                        .description("")
                        .build())
                .build(), json.get(0));
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("map_entry")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A.map_entry")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("key")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .build())
                .addFields(ImmutableMessageField.builder()
                        .name("value")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(2)
                        .map(false)
                        .description("")
                        .build())
                .putOptions("map_entry", true)
                .addUsages(ImmutableUsageItem.builder()
                        .ref("protostuff_unittest.A")
                        .type(UsageType.MESSAGE)
                        .build())
                .build(), json.get(1));
    }

    @Test
    void oneof() {
        compile(messageGenerator, "protostuff_unittest/oneof.proto");
        Assertions.assertEquals(1, json.size());
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("A")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A")
                .description("")
                .addFields(ImmutableMessageField.builder()
                        .name("a")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("")
                        .oneof("oneof")
                        .build())
                .addFields(ImmutableMessageField.builder()
                        .name("b")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(2)
                        .map(false)
                        .description("")
                        .oneof("oneof")
                        .build())
                .build(), json.get(0));
    }

    @Test
    void messageComments() {
        compile(messageGenerator, "protostuff_unittest/comments.proto");
        Assertions.assertEquals(1, json.size());
        Assertions.assertEquals(ImmutableMessageDescriptor.builder()
                .name("A")
                .type(NodeType.MESSAGE)
                .canonicalName("protostuff_unittest.A")
                .description("message comment")
                .addFields(ImmutableMessageField.builder()
                        .name("field")
                        .typeId("int32")
                        .modifier(MessageFieldModifier.OPTIONAL)
                        .tag(1)
                        .map(false)
                        .description("field comment")
                        .build())
                .build(), json.get(0));
    }
}