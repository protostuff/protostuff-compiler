package io.protostuff.generator.java;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageUtilTest {

    @Test
    public void testBitFieldNames() throws Exception {
        Message m = new Message(null);
        assertEquals(0, MessageUtil.bitFieldNames(m).size());
        m.addField(new Field(m)); // 1
        assertEquals(1, MessageUtil.bitFieldNames(m).size());
        for (int i = 0; i < 31; i++) {
            m.addField(new Field(m)); // 32
        }
        assertEquals(1, MessageUtil.bitFieldNames(m).size());
        m.addField(new Field(m)); // 33
        List<String> bitFieldNames = MessageUtil.bitFieldNames(m);
        assertEquals(2, bitFieldNames.size());
        assertEquals("__bitField0", bitFieldNames.get(0));
        assertEquals("__bitField1", bitFieldNames.get(1));
    }
}