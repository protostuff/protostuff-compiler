package io.protostuff.generator.java;

import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Package;
import io.protostuff.compiler.model.Proto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class UserTypeUtilTest {

    private Proto proto;
    private Message message;
    private Message nestedMessage;

    @BeforeEach
    void setUp() {
        proto = new Proto();
        proto.setPackage(new Package(proto, "io.protostuff"));
        message = new Message(proto);
        message.setProto(proto);
        message.setName("message_name");
        nestedMessage = new Message(message);
        nestedMessage.setProto(proto);
        nestedMessage.setName("nested_message");
    }

    @Test
    void getClassName() {
        String name = UserTypeUtil.getClassName(message);
        assertEquals("MessageName", name);
    }

    @Test
    void getCanonicalName() {
        String name = UserTypeUtil.getCanonicalName(message);
        assertEquals("io.protostuff.MessageName", name);
    }

    @Test
    void getCanonicalName_nestedMessage() {
        String name = UserTypeUtil.getCanonicalName(nestedMessage);
        assertEquals("io.protostuff.MessageName.NestedMessage", name);
    }

    @Test
    void getCanonicalName_packageIsEmpty() {
        proto.setPackage(new Package(proto, ""));
        String name = UserTypeUtil.getCanonicalName(message);
        assertEquals("MessageName", name);
    }

    @Test
    void getCanonicalName_packageIsSetByOption() {
        proto.getOptions().set("java_package", DynamicMessage.Value.createString("java.package"));
        String name = UserTypeUtil.getCanonicalName(message);
        assertEquals("java.package.MessageName", name);
    }

}