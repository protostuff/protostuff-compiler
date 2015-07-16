package io.protostuff.compiler.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.protostuff.compiler.model.DynamicMessage.Value.createString;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class DynamicMessageTest {

    private DynamicMessage m;

    @Before
    public void setUp() throws Exception {
        m = new DynamicMessage();
    }

    @Test
    public void testSimpleKeyValue() throws Exception {
        m.set("key", createString("value"));
        Assert.assertEquals("value", m.get("key").getString());
    }

    @Test
    public void testFieldOfField() throws Exception {
        m.set("key.subkey", createString("value"));
        Assert.assertEquals("value", m.get("key.subkey").getString());
        DynamicMessage n = m.get("key").getMessage();
        Assert.assertEquals("value", n.get("subkey").getString());
    }

    @Test
    public void testExtensionKeyValue() throws Exception {
        m.set("(.io.protostuff.key)", createString("value"));
        Assert.assertEquals("value", m.get("(.io.protostuff.key)").getString());
    }

    @Test
    public void testExtensionFieldOfField() throws Exception {
        m.set("key.(.io.protostuff.subkey)", createString("value"));
        Assert.assertEquals("value", m.get("key.(.io.protostuff.subkey)").getString());
        DynamicMessage n = m.get("key").getMessage();
        Assert.assertEquals("value", n.get("(.io.protostuff.subkey)").getString());
    }

    @Test
    public void testFieldOfExtensionField() throws Exception {
        m.set("(.io.protostuff.key).subkey", createString("value"));
        Assert.assertEquals("value", m.get("(.io.protostuff.key).subkey").getString());
        DynamicMessage n = m.get("(.io.protostuff.key)").getMessage();
        Assert.assertEquals("value", n.get("subkey").getString());
    }

}