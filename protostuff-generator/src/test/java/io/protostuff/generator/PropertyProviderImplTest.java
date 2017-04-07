package io.protostuff.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class PropertyProviderImplTest {

    private static final String PROPERTY = "property";
    private static final String VALUE = "value";
    private PropertyProviderImpl provider;

    @BeforeEach
    void setUp() {
        provider = new PropertyProviderImpl();
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