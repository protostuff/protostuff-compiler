package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoWalker {

    private final ProtoContext context;
    private final Proto proto;

    private final List<Processor<Proto>> protoProcessors = new ArrayList<>();
    private final List<Processor<Message>> messageProcessors = new ArrayList<>();
    private final List<Processor<Enum>> enumProcessors = new ArrayList<>();
    private final List<Processor<Service>> serviceProcessors = new ArrayList<>();

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

    public ProtoWalker onEnum(Processor<Enum> processor) {
        enumProcessors.add(processor);
        return this;
    }

    public ProtoWalker onService(Processor<Service> processor) {
        serviceProcessors.add(processor);
        return this;
    }

    public void walk() {
        for (Processor<Proto> protoProcessor : protoProcessors) {
            protoProcessor.run(context, proto);
        }
        walk(proto);
    }

    private void walk(Proto container) {
        List<Service> services = container.getServices();
        for (Processor<Service> serviceProcessor : serviceProcessors) {
            for (Service service : services) {
                serviceProcessor.run(context, service);
            }
        }
        walk((UserTypeContainer) container);
    }

    private void walk(UserTypeContainer container) {
        List<Message> messages = container.getMessages();
        for (Processor<Message> messageProcessor : messageProcessors) {
            for (Message message : messages) {
                messageProcessor.run(context, message);
            }
        }
        List<Enum> enums = container.getEnums();
        for (Processor<Enum> enumProcessor : enumProcessors) {
            for (Enum anEnum : enums) {
                enumProcessor.run(context, anEnum);
            }
        }
    }

    public interface Processor<T> {

        void run(ProtoContext context, T t);
    }
}
