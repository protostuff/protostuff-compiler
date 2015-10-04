package io.protostuff.it;

import org.junit.Assert;
import org.junit.Test;

import io.protostuff.it.enum_test.NestedEnum;
import io.protostuff.it.enum_test.ParentEnumMsg;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumTest {

    @Test
    public void defaultFieldValue() throws Exception {
        ParentEnumMsg msg = ParentEnumMsg.getDefaultInstance();
        Assert.assertEquals(NestedEnum.UNKNOWN, msg.getNestedEnum());
    }
}
