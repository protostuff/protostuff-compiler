package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class UserTypeValidationPostProcessorTest {

    private UserTypeValidationPostProcessor processor;

    @Before
    public void setUp() throws Exception {
        processor = new UserTypeValidationPostProcessor();
    }

    @Test
    public void isValidTagValue() throws Exception {
        assertFalse(processor.isValidTagValue(-1));
        assertFalse(processor.isValidTagValue(0));
        assertFalse(processor.isValidTagValue(19000));
        assertFalse(processor.isValidTagValue(19999));
        assertFalse(processor.isValidTagValue(Field.MAX_TAG_VALUE+1));
        assertTrue(processor.isValidTagValue(1));
        assertTrue(processor.isValidTagValue(100));
        assertTrue(processor.isValidTagValue(18999));
        assertTrue(processor.isValidTagValue(20000));
        assertTrue(processor.isValidTagValue(Field.MAX_TAG_VALUE-1));
        assertTrue(processor.isValidTagValue(Field.MAX_TAG_VALUE));
    }

}