package io.protostuff.compiler.model.util;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.UserType;
import io.protostuff.compiler.model.UserTypeContainer;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoTreeWalker {

    public static final ProtoTreeWalker DEFAULT = new ProtoTreeWalker();

    public void walk(UserTypeContainer container, Operation operation) {
        for (Message message : container.getMessages()) {
            operation.process(container, message);
        }
        for (Enum anEnum : container.getEnums()) {
            operation.process(container, anEnum);
        }
        for (Message message : container.getMessages()) {
            walk(message, operation);
        }
    }

    public interface Operation {
        void process(UserTypeContainer container, UserType type);
    }
}
