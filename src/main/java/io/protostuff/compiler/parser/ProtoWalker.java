package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.UserTypeContainer;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoWalker {

    private final ProtoContext context;
    private final Proto proto;

    private final List<Processor<Message>> messageProcessors = new ArrayList<>();

    public ProtoWalker(ProtoContext protoContext) {
        this.context = protoContext;
        this.proto = protoContext.getProto();
    }

    public static ProtoWalker newInstance(ProtoContext proto) {
        return new ProtoWalker(proto);
    }

    public ProtoWalker onMessage(Processor<Message> processor) {
        messageProcessors.add(processor);
        return this;
    }

    public void walk() {
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
