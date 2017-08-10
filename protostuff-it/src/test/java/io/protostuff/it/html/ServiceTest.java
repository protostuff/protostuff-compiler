package io.protostuff.it.html;

import com.google.common.collect.ImmutableMap;
import io.protostuff.generator.html.json.index.NodeType;
import io.protostuff.generator.html.json.service.ImmutableServiceDescriptor;
import io.protostuff.generator.html.json.service.ImmutableServiceMethod;
import io.protostuff.generator.html.json.service.ServiceDescriptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceTest {

    @Test
    public void name() throws Exception {
        ServiceDescriptor service = HtmlGeneratorUtil.read(ServiceDescriptor.class, "html/data/type/io.protostuff.it.TestService.json");
        ImmutableServiceDescriptor expected = ImmutableServiceDescriptor.builder()
                .name("TestService")
                .type(NodeType.SERVICE)
                .canonicalName("io.protostuff.it.TestService")
                .description("")
                .addMethods(ImmutableServiceMethod.builder()
                        .name("test")
                        .argTypeId("io.protostuff.it.RequestMessage")
                        .returnTypeId("io.protostuff.it.ResponseMessage")
                        .description("")
                        .build())
                .addMethods(ImmutableServiceMethod.builder()
                        .name("testServerStream")
                        .argTypeId("io.protostuff.it.RequestMessage")
                        .returnTypeId("io.protostuff.it.ResponseMessage")
                        .returnStream(true)
                        .description("")
                        .build())
                .addMethods(ImmutableServiceMethod.builder()
                        .name("testClientStream")
                        .argTypeId("io.protostuff.it.RequestMessage")
                        .argStream(true)
                        .returnTypeId("io.protostuff.it.ResponseMessage")
                        .description("")
                        .build())
                .addMethods(ImmutableServiceMethod.builder()
                        .name("testBidiStream")
                        .argTypeId("io.protostuff.it.RequestMessage")
                        .argStream(true)
                        .returnTypeId("io.protostuff.it.ResponseMessage")
                        .returnStream(true)
                        .description("")
                        .build())
                .addMethods(ImmutableServiceMethod.builder()
                        .name("deprecated")
                        .argTypeId("io.protostuff.it.RequestMessage")
                        .returnTypeId("io.protostuff.it.ResponseMessage")
                        .description("")
                        .options(ImmutableMap.of("deprecated", true))
                        .build())
                .addMethods(ImmutableServiceMethod.builder()
                        .name("custom")
                        .argTypeId("io.protostuff.it.RequestMessage")
                        .returnTypeId("io.protostuff.it.ResponseMessage")
                        .description("")
                        .options(ImmutableMap.of("(io.protostuff.it.customOption)", "test"))
                        .build())
                .build();
        Assertions.assertEquals(expected, service);
    }
}
