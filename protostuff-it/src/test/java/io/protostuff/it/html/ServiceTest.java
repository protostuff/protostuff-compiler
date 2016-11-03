package io.protostuff.it.html;

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
                .build();
        Assertions.assertEquals(expected, service);
    }
}
