package io.protostuff.generator.java;

import io.protostuff.compiler.model.Field;
import io.protostuff.compiler.model.Message;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageUtilTest {

    @Test
    public void testBitFieldNames() throws Exception {
        Message m = new Message(null);
        Assert.assertEquals(0, MessageUtil.bitFieldNames(m).size());
        m.addField(new Field(m)); // 1
        Assert.assertEquals(1, MessageUtil.bitFieldNames(m).size());
        for (int i = 0; i < 31; i++) {
            m.addField(new Field(m)); // 32
        }
        Assert.assertEquals(1, MessageUtil.bitFieldNames(m).size());
        m.addField(new Field(m)); // 33
        List<String> bitFieldNames = MessageUtil.bitFieldNames(m);
        Assert.assertEquals(2, bitFieldNames.size());
        Assert.assertEquals("__bitField0", bitFieldNames.get(0));
        Assert.assertEquals("__bitField1", bitFieldNames.get(1));
    }
}