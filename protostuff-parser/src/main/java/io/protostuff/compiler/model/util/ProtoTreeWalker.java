package io.protostuff.compiler.model.util;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.UserTypeContainer;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoTreeWalker {

    public static final ProtoTreeWalker DEFAULT = new ProtoTreeWalker();

    public void walk(Proto proto,
                     ProtoOperation protoOperation,
                     MessageOperation messageOperation,
                     EnumOperation enumOperation) {
        protoOperation.process(proto);
        for (Enum anEnum : proto.getEnums()) {
            enumOperation.process(anEnum);
        }
        for (Message message : proto.getMessages()) {
            messageOperation.process(message);
        }
        for (Message message : proto.getMessages()) {
            walk(message, messageOperation, enumOperation);
        }
    }

    public void walk(UserTypeContainer container,
                     MessageOperation messageOperation,
                     EnumOperation enumOperation) {
        for (Message message : container.getMessages()) {
            messageOperation.process(message);
        }
        for (Enum anEnum : container.getEnums()) {
            enumOperation.process(anEnum);
        }
        for (Message message : container.getMessages()) {
            walk(message, messageOperation, enumOperation);
        }
    }

    public interface ProtoOperation {
        void process(Proto proto);
    }

    public interface MessageOperation {
        void process(Message message);
    }

    public interface EnumOperation {
        void process(Enum e);
    }


}
