package io.protostuff.it;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import io.protostuff.it.enum_test.NestedEnum;
import io.protostuff.it.enum_test.ParentEnumMsg;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void defaultFieldValue() throws Exception {
        ParentEnumMsg msg = ParentEnumMsg.newBuilder().build();
        Assert.assertEquals(NestedEnum.ZERO, msg.getNestedEnum());
    }

    @Test
    public void failWhenSetEnumValueToUnknownInBuilder_singluar() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        ParentEnumMsg.newBuilder()
                .setNestedEnum(NestedEnum.UNRECOGNIZED)
                .build();
    }

    @Test
    public void failWhenSetEnumValueToUnknownInBuilder_repeated_add() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        ParentEnumMsg.newBuilder()
                .addNestedRepeatedEnum(NestedEnum.UNRECOGNIZED)
                .build();
    }

    @Test
    public void failWhenSetEnumValueToUnknownInBuilder_repeated_set() throws Exception {
        ParentEnumMsg.Builder builder = ParentEnumMsg.newBuilder()
                .addNestedRepeatedEnum(NestedEnum.FIRST);
        thrown.expect(IllegalArgumentException.class);
        builder.setNestedRepeatedEnum(0, NestedEnum.UNRECOGNIZED);
    }

    @Test
    public void failWhenSetEnumValueToUnknownInBuilder_repeated_add_all() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        ParentEnumMsg.newBuilder()
                .addAllNestedRepeatedEnum(Collections.singletonList(NestedEnum.UNRECOGNIZED))
                .build();
    }

    @Test
    public void failWhenSetEnumValueToUnknownInBuilder_oneof() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        ParentEnumMsg.newBuilder()
                .setFirst(NestedEnum.UNRECOGNIZED)
                .build();
    }

    @Test
    public void enumValueTest_normalField() throws Exception {
        ParentEnumMsg msg = ParentEnumMsg.newBuilder()
                .setNestedEnumValue(42)
                .build();
        Assert.assertTrue(msg.hasNestedEnum());
        Assert.assertEquals(NestedEnum.UNRECOGNIZED, msg.getNestedEnum());
        Assert.assertEquals(42, msg.getNestedEnumValue());
    }

    @Test
    public void enumValueTest_oneofField() throws Exception {
        ParentEnumMsg msg = ParentEnumMsg.newBuilder()
                .setFirstValue(42)
                .build();
        Assert.assertTrue(msg.hasFirst());
        Assert.assertEquals(NestedEnum.UNRECOGNIZED, msg.getFirst());
        Assert.assertEquals(42, msg.getFirstValue());
    }

    @Test
    public void enumValueTest_repeated_adder() throws Exception {
        ParentEnumMsg msg = ParentEnumMsg.newBuilder()
                .addNestedRepeatedEnumValue(42)
                .build();
        Assert.assertEquals(NestedEnum.UNRECOGNIZED, msg.getNestedRepeatedEnum(0));
        Assert.assertEquals(42, msg.getNestedRepeatedEnumValue(0));
    }

    @Test
    public void enumValueTest_repeated_addAll() throws Exception {
        ParentEnumMsg msg = ParentEnumMsg.newBuilder()
                .addAllNestedRepeatedEnumValue(Collections.singletonList(42))
                .build();
        Assert.assertEquals(NestedEnum.UNRECOGNIZED, msg.getNestedRepeatedEnum(0));
        Assert.assertEquals(42, msg.getNestedRepeatedEnumValue(0));
    }

    @Test
    public void enumValueTest_repeated_setByIndex() throws Exception {
        ParentEnumMsg msg = ParentEnumMsg.newBuilder()
                .addNestedRepeatedEnum(NestedEnum.FIRST)
                .setNestedRepeatedEnumValue(0, 42)
                .build();
        Assert.assertEquals(NestedEnum.UNRECOGNIZED, msg.getNestedRepeatedEnum(0));
        Assert.assertEquals(42, msg.getNestedRepeatedEnumValue(0));
    }

}
