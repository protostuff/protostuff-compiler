package io.protostuff.it.html;

import io.protostuff.generator.html.json.index.NodeType;
import io.protostuff.generator.html.json.message.ImmutableMessageDescriptor;
import io.protostuff.generator.html.json.message.MessageDescriptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeLinkTest {

    @Test
    void wikiStyleLinkIsConvertedToUrl() {
        MessageDescriptor service = HtmlGeneratorUtil.read(MessageDescriptor.class, "html/data/type/io.protostuff.it.TypeLinkTestMessage.json");
        MessageDescriptor expected = ImmutableMessageDescriptor.builder()
                .name("TypeLinkTestMessage")
                .type(NodeType.MESSAGE)
                .canonicalName("io.protostuff.it.TypeLinkTestMessage")
                .description("<p><a href=\"#/types/io.protostuff.it.TypeLinkTestMessage\">io.protostuff.it.TypeLinkTestMessage</a></p>")
                .build();
        Assertions.assertEquals(expected, service);
    }
}
