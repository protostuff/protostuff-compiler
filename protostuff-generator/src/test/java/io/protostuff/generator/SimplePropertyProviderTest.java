package io.protostuff.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class SimplePropertyProviderTest {

    private static final String PROPERTY = "property";
    private static final String VALUE = "value";
    private SimplePropertyProvider provider;

    @BeforeEach
    void setUp() {
        provider = new SimplePropertyProvider();
    }

    @Test
    void hasProperty() {
        provider.register(PROPERTY, key -> VALUE);
        assertTrue(provider.hasProperty(PROPERTY));
        assertFalse(provider.hasProperty("does not exist"));
    }

    @Test
    void getProperty() {
        provider.register(PROPERTY, key -> VALUE);
        assertEquals(VALUE, provider.getProperty(null, PROPERTY));
    }

}