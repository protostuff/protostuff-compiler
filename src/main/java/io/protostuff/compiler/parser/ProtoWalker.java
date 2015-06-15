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

    private final List<Consumer<Message>> messageProcessors = new ArrayList<>();

    public ProtoWalker(ProtoContext protoContext) {
        this.context = protoContext;
        this.proto = protoContext.getProto();
    }

    public static ProtoWalker newInstance(ProtoContext proto) {
        return new ProtoWalker(proto);
    }

    public ProtoWalker onMessage(Consumer<Message> processor) {
        messageProcessors.add(processor);
        return this;
    }

    public void walk() {
        walk(proto);
    }

    private void walk(UserTypeContainer container) {
        for (Consumer<Message> messageProcessor : messageProcessors) {
            List<Message> messages = container.getMessages();
            messages.forEach(messageProcessor::accept);
        }
    }


}
