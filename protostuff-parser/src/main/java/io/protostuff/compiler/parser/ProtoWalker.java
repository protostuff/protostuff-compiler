package io.protostuff.compiler.parser;

import java.util.ArrayList;
import java.util.List;

import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.UserTypeContainer;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoWalker {

    private final ProtoContext context;
    private final Proto proto;

    private final List<Processor<Proto>> protoProcessors = new ArrayList<>();
    private final List<Processor<Message>> messageProcessors = new ArrayList<>();

    public ProtoWalker(ProtoContext protoContext) {
        this.context = protoContext;
        this.proto = protoContext.getProto();
    }

    public static ProtoWalker newInstance(ProtoContext proto) {
        return new ProtoWalker(proto);
    }

    public ProtoWalker onProto(Processor<Proto> processor) {
        protoProcessors.add(processor);
        return this;
    }

    public ProtoWalker onMessage(Processor<Message> processor) {
        messageProcessors.add(processor);
        return this;
    }

    public void walk() {
        for (Processor<Proto> protoProcessor : protoProcessors) {
            protoProcessor.run(context, proto);
        }
        walk(proto);
    }

    private void walk(UserTypeContainer container) {
        for (Processor<Message> messageProcessor : messageProcessors) {
            List<Message> messages = container.getMessages();
            for (Message message : messages) {
                messageProcessor.run(context, message);
            }
        }
    }


    public interface Processor<T> {

        void run(ProtoContext context, T t);
    }
}
