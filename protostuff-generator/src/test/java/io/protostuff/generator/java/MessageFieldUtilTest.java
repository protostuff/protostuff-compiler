package io.protostuff.generator.java;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.*;
import io.protostuff.compiler.model.Package;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageFieldUtilTest {

    private Field f1;
    private Field f32;
    private Field f33;

    @BeforeEach
    public void setUp() throws Exception {
        f1 = new Field(null);
        f1.setIndex(1);
        f32 = new Field(null);
        f32.setIndex(32);
        f33 = new Field(null);
        f33.setIndex(33);
    }

    @Test
    public void testBitFieldName() throws Exception {
        assertEquals("__bitField0", MessageFieldUtil.bitFieldName(f1));
        assertEquals("__bitField0", MessageFieldUtil.bitFieldName(f32));
        assertEquals("__bitField1", MessageFieldUtil.bitFieldName(f33));
    }

    @Test
    public void testBitFieldIndex() throws Exception {
        assertEquals(0, MessageFieldUtil.bitFieldIndex(f1));
        assertEquals(31, MessageFieldUtil.bitFieldIndex(f32));
        assertEquals(0, MessageFieldUtil.bitFieldIndex(f33));
    }

    @Test
    public void testBitFieldMask() throws Exception {
        assertEquals(1, MessageFieldUtil.bitFieldMask(f1));
        assertEquals(-2147483648, MessageFieldUtil.bitFieldMask(f32));
        assertEquals(1, MessageFieldUtil.bitFieldMask(f33));
    }

    @Test
    void getFieldType_scalar() {
        Field field = new Field(null);
        field.setType(ScalarFieldType.INT32);
        assertEquals("int", MessageFieldUtil.getFieldType(field));
    }

    @Test
    void getFieldType_message() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Message message = new Message(proto);
        message.setName("Message");
        message.setProto(proto);
        Field field = new Field(null);
        field.setType(message);
        assertEquals("package.Message", MessageFieldUtil.getFieldType(field));
    }

    @Test
    void getFieldName() {
        Field field = new Field(null);
        field.setName("field_name");
        assertEquals("fieldName", MessageFieldUtil.getFieldName(field));
    }

    @Test
    void getFieldName_reserved_keyword() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("interface_", MessageFieldUtil.getFieldName(field));
    }

    @Test
    void getJsonFieldName() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("interface", MessageFieldUtil.getJsonFieldName(field));
    }

    @Test
    void getFieldGetterName() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("getInterface", MessageFieldUtil.getFieldGetterName(field));
    }

    @Test
    void getFieldSetterName() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("setInterface", MessageFieldUtil.getFieldSetterName(field));
    }

    @Test
    void getFieldGetterName_conflict_with_getClass() {
        Field field = new Field(null);
        field.setName("class");
        assertEquals("getClass_", MessageFieldUtil.getFieldGetterName(field));
    }

    @Test
    void getFieldCleanerName() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("clearInterface", MessageFieldUtil.getFieldCleanerName(field));
    }

    @Test
    void getFieldHasMethodName() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("hasInterface", MessageFieldUtil.getHasMethodName(field));
    }

    @Test
    void isMessage_scalar() {
        Field field = new Field(null);
        field.setType(ScalarFieldType.INT32);
        assertFalse(MessageFieldUtil.isMessage(field));
    }

    @Test
    void isMessage_message() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Message message = new Message(proto);
        message.setName("Message");
        message.setProto(proto);
        Field field = new Field(null);
        field.setType(message);
        assertTrue(MessageFieldUtil.isMessage(field));
    }

    @Test
    void isMessage_enum() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Enum anEnum = new Enum(proto);
        anEnum.setName("Enum");
        anEnum.setProto(proto);
        Field field = new Field(null);
        field.setType(anEnum);
        assertFalse(MessageFieldUtil.isMessage(field));
    }

    @Test
    void getBuilderGetterName() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("getInterface", MessageFieldUtil.getBuilderGetterName(field));
    }

    @Test
    void getBuilderSetterName() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("setInterface", MessageFieldUtil.getBuilderSetterName(field));
    }

    @Test
    void getRepeatedBuilderSetterName() {
        Field field = new Field(null);
        field.setName("interface");
        field.setModifier(FieldModifier.REPEATED);
        assertEquals("setInterfaceList", MessageFieldUtil.getRepeatedBuilderSetterName(field));
    }

    @Test
    void getEnumFieldValueGetterName() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("getInterfaceValue", MessageFieldUtil.getEnumFieldValueGetterName(field));
    }

    @Test
    void getEnumFieldValueSetterName() {
        Field field = new Field(null);
        field.setName("interface");
        assertEquals("setInterfaceValue", MessageFieldUtil.getEnumFieldValueSetterName(field));
    }

    @Test
    void getDefaultValue_scalar() {
        Field field = new Field(null);
        field.setType(ScalarFieldType.INT32);
        assertEquals("0",
                MessageFieldUtil.getDefaultValue(field));
    }

    @Test
    void getDefaultValue_message() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Message message = new Message(proto);
        message.setName("Message");
        message.setProto(proto);
        Field field = new Field(null);
        field.setType(message);
        assertEquals("package.Message.getDefaultInstance()",
                MessageFieldUtil.getDefaultValue(field));
    }

    @Test
    void getDefaultValue_enum_empty() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Enum anEnum = new Enum(proto);
        anEnum.setName("Enum");
        anEnum.setProto(proto);
        Field field = new Field(null);
        field.setType(anEnum);
        assertEquals("package.Enum.UNRECOGNIZED",
                MessageFieldUtil.getDefaultValue(field));
    }

    @Test
    void getDefaultValue_enum() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Enum anEnum = new Enum(proto);
        anEnum.setName("Enum");
        anEnum.setProto(proto);
        anEnum.addConstant(createEnumConstant(anEnum, "A", 0));
        anEnum.addConstant(createEnumConstant(anEnum, "B", 1));
        Field field = new Field(null);
        field.setType(anEnum);
        assertEquals("package.Enum.A",
                MessageFieldUtil.getDefaultValue(field));
    }

    private EnumConstant createEnumConstant(Enum anEnum, String name, int value) {
        EnumConstant a = new EnumConstant(anEnum);
        a.setName(name);
        a.setValue(value);
        return a;
    }

    @Test
    void isScalarNullableType_string() {
        Field field = new Field(null);
        field.setType(ScalarFieldType.STRING);
        assertTrue(MessageFieldUtil.isScalarNullableType(field));
    }

    @Test
    void isScalarNullableType_bytes() {
        Field field = new Field(null);
        field.setType(ScalarFieldType.BYTES);
        assertTrue(MessageFieldUtil.isScalarNullableType(field));
    }

    @Test
    void isScalarNullableType_enum() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Enum anEnum = new Enum(proto);
        anEnum.setName("Enum");
        anEnum.setProto(proto);
        anEnum.addConstant(createEnumConstant(anEnum, "A", 0));
        Field field = new Field(null);
        field.setType(anEnum);
        assertTrue(MessageFieldUtil.isScalarNullableType(field));
    }

    @Test
    void isScalarNullableType_int() {
        Field field = new Field(null);
        field.setType(ScalarFieldType.INT32);
        assertFalse(MessageFieldUtil.isScalarNullableType(field));
    }

    @Test
    void getRepeatedFieldType_enum() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Enum anEnum = new Enum(proto);
        anEnum.setName("Enum");
        anEnum.setProto(proto);
        anEnum.addConstant(createEnumConstant(anEnum, "A", 0));
        Field field = new Field(null);
        field.setModifier(FieldModifier.REPEATED);
        field.setType(anEnum);
        assertEquals("java.util.List<package.Enum>", MessageFieldUtil.getRepeatedFieldType(field));
    }

    @Test
    void getRepeatedFieldType_scalar() {
        Field field = new Field(null);
        field.setModifier(FieldModifier.REPEATED);
        field.setType(ScalarFieldType.INT32);
        assertEquals("java.util.List<Integer>", MessageFieldUtil.getRepeatedFieldType(field));
    }

    @Test
    void getIterableFieldType_enum() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Enum anEnum = new Enum(proto);
        anEnum.setName("Enum");
        anEnum.setProto(proto);
        anEnum.addConstant(createEnumConstant(anEnum, "A", 0));
        Field field = new Field(null);
        field.setModifier(FieldModifier.REPEATED);
        field.setType(anEnum);
        assertEquals("java.lang.Iterable<package.Enum>", MessageFieldUtil.getIterableFieldType(field));
    }

    @Test
    void getIterableFieldType_scalar() {
        Field field = new Field(null);
        field.setModifier(FieldModifier.REPEATED);
        field.setType(ScalarFieldType.INT32);
        assertEquals("java.lang.Iterable<Integer>", MessageFieldUtil.getIterableFieldType(field));
    }

    @Test
    void getWrapperFieldType_enum() {
        Proto proto = new Proto();
        proto.setPackage(new Package(proto, "package"));
        Enum anEnum = new Enum(proto);
        anEnum.setName("Enum");
        anEnum.setProto(proto);
        anEnum.addConstant(createEnumConstant(anEnum, "A", 0));
        Field field = new Field(null);
        field.setModifier(FieldModifier.REPEATED);
        field.setType(anEnum);
        assertEquals("package.Enum", MessageFieldUtil.getWrapperFieldType(field));
    }

    @Test
    void getWrapperFieldType_scalar() {
        Field field = new Field(null);
        field.setModifier(FieldModifier.REPEATED);
        field.setType(ScalarFieldType.INT32);
        assertEquals("Integer", MessageFieldUtil.getWrapperFieldType(field));
    }
}