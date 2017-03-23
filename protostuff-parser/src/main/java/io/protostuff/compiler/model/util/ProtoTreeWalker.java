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
        proto.getEnums().forEach(enumOperation::process);
        proto.getMessages().forEach(messageOperation::process);
        for (Message message : proto.getMessages()) {
            walk(message, messageOperation, enumOperation);
        }
    }

    public void walk(UserTypeContainer container,
                     MessageOperation messageOperation,
                     EnumOperation enumOperation) {

        container.getMessages().forEach(messageOperation::process);
        container.getEnums().forEach(enumOperation::process);
        for (Message message : container.getMessages()) {
            walk(message, messageOperation, enumOperation);
        }
    }

    @FunctionalInterface
    public interface ProtoOperation {
        void process(Proto proto);
    }

    @FunctionalInterface
    public interface MessageOperation {
        void process(Message message);
    }

    @FunctionalInterface
    public interface EnumOperation {
        void process(Enum e);
    }


}
