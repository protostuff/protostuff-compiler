package io.protostuff.generator.html.json.message;

import io.protostuff.compiler.model.Module;
import io.protostuff.generator.html.json.index.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonIndexGeneratorTest extends AbstractJsonGeneratorTest {


    JsonIndexGenerator generator;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        generator = new JsonIndexGenerator(null) {
            @Override
            protected void write(Module module, String file, Object data) {
                json.add(data);
            }
        };
    }

    @Test
    void index() {
        compile(generator, "protostuff_unittest/index.proto");
        JsonTreeNode expected = ImmutableJsonTreeNode.builder()
                .label("protostuff_unittest/index.proto")
                .data(ImmutableNodeData.builder()
                        .type(NodeType.PROTO)
                        .ref("protostuff_unittest.index")
                        .build())
                .addChildren(ImmutableJsonTreeNode.builder()
                        .label("Service")
                        .data(ImmutableNodeData.builder()
                                .type(NodeType.SERVICE)
                                .ref("protostuff_unittest.Service")
                                .build())
                        .build())
                .addChildren(ImmutableJsonTreeNode.builder()
                        .label("E")
                        .data(ImmutableNodeData.builder()
                                .type(NodeType.ENUM)
                                .ref("protostuff_unittest.E")
                                .build())
                        .build())
                .addChildren(ImmutableJsonTreeNode.builder()
                        .label("A")
                        .data(ImmutableNodeData.builder()
                                .type(NodeType.MESSAGE)
                                .ref("protostuff_unittest.A")
                                .build())
                        .build())
                .addChildren(ImmutableJsonTreeNode.builder()
                        .label("B")
                        .data(ImmutableNodeData.builder()
                                .type(NodeType.MESSAGE)
                                .ref("protostuff_unittest.B")
                                .build())
                        .build())
                .addChildren(ImmutableJsonTreeNode.builder()
                        .label("C")
                        .data(ImmutableNodeData.builder()
                                .type(NodeType.MESSAGE)
                                .ref("protostuff_unittest.C")
                                .build())
                        .build())
                .build();
        Assertions.assertEquals(expected, ((List) json.get(0)).get(0));
    }
}
