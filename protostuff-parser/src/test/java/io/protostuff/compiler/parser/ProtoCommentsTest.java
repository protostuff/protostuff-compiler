package io.protostuff.compiler.parser;

import org.junit.Test;

import io.protostuff.compiler.model.Proto;

import static org.junit.Assert.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoCommentsTest extends AbstractParserTest {

    @Test
    public void firstCommentInFileBelongsToMessage() throws Exception {
        ProtoContext context = importer.importFile(new ClasspathFileReader(),
                "protostuff_unittest/proto_comments_belong_to_message.proto");
        Proto proto = context.getProto();
        assertEquals("", proto.getComments());
    }

}
