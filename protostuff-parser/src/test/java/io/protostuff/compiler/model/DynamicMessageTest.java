package io.protostuff.compiler.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.protostuff.compiler.model.DynamicMessage.Value.createString;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class DynamicMessageTest {

    private DynamicMessage m;

    @BeforeEach
    public void setUp() throws Exception {
        m = new DynamicMessage();
    }

    @Test
    public void testSimpleKeyValue() throws Exception {
        m.set("key", createString("value"));
        assertEquals("value", m.get("key").getString());
    }

    @Test
    public void testFieldOfField() throws Exception {
        m.set("key.subkey", createString("value"));
        assertEquals("value", m.get("key.subkey").getString());
        DynamicMessage n = m.get("key").getMessage();
        assertEquals("value", n.get("subkey").getString());
    }

    @Test
    public void testExtensionKeyValue() throws Exception {
        m.set("(.io.protostuff.key)", createString("value"));
        assertEquals("value", m.get("(.io.protostuff.key)").getString());
    }

    @Test
    public void testExtensionFieldOfField() throws Exception {
        m.set("key.(.io.protostuff.subkey)", createString("value"));
        assertEquals("value", m.get("key.(.io.protostuff.subkey)").getString());
        DynamicMessage n = m.get("key").getMessage();
        assertEquals("value", n.get("(.io.protostuff.subkey)").getString());
    }

    @Test
    public void testFieldOfExtensionField() throws Exception {
        m.set("(.io.protostuff.key).subkey", createString("value"));
        assertEquals("value", m.get("(.io.protostuff.key).subkey").getString());
        DynamicMessage n = m.get("(.io.protostuff.key)").getMessage();
        assertEquals("value", n.get("subkey").getString());
    }

}