package io.protostuff.generator.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.protostuff.compiler.model.ScalarFieldType.INT32;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class ScalarFieldTypeUtilTest {

    @Test
    void getWrapperType() {
        assertEquals("Integer", ScalarFieldTypeUtil.getWrapperType(INT32));
    }

    @Test
    void getWrapperType_null_input() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ScalarFieldTypeUtil.getWrapperType(null));
    }

    @Test
    void getPrimitiveType() {
        assertEquals("int", ScalarFieldTypeUtil.getPrimitiveType(INT32));
    }

    @Test
    void getDefaultValue() {
        assertEquals("0", ScalarFieldTypeUtil.getDefaultValue(INT32));
    }

}