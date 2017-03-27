package io.protostuff.generator.html.json.message;

import io.protostuff.compiler.model.Module;
import io.protostuff.generator.html.json.enumeration.ImmutableEnumConstant;
import io.protostuff.generator.html.json.enumeration.ImmutableEnumDescriptor;
import io.protostuff.generator.html.json.enumeration.JsonEnumGenerator;
import io.protostuff.generator.html.json.index.NodeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class JsonEnumGeneratorTest extends AbstractJsonGeneratorTest {

    JsonEnumGenerator enumGenerator;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        enumGenerator = new JsonEnumGenerator(null, markdownProcessor) {
            @Override
            protected void write(Module module, String file, Object data) {
                json.add(data);
            }
        };
    }

    @Test
    void testSample() {
        compile(enumGenerator, "protostuff_unittest/messages_sample.proto");
        Assertions.assertEquals(2, json.size());
        Assertions.assertEquals(ImmutableEnumDescriptor.builder()
                .name("E")
                .type(NodeType.ENUM)
                .canonicalName("protostuff_unittest.E")
                .description("")
                .addConstants(ImmutableEnumConstant.builder()
                        .name("X")
                        .description("")
                        .value(0)
                        .build())
                .build(), json.get(0));
        Assertions.assertEquals(ImmutableEnumDescriptor.builder()
                .name("E")
                .type(NodeType.ENUM)
                .canonicalName("protostuff_unittest.A.B.C.D.E")
                .description("")
                .addConstants(ImmutableEnumConstant.builder()
                        .name("X")
                        .description("")
                        .value(0)
                        .build())
                .build(), json.get(1));
    }

}