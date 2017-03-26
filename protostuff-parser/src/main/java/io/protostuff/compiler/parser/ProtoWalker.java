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
    private final List<Processor<Field>> fieldProcessors = new ArrayList<>();
    private final List<Processor<Enum>> enumProcessors = new ArrayList<>();
    private final List<Processor<EnumConstant>> enumConstantProcessors = new ArrayList<>();
    private final List<Processor<Service>> serviceProcessors = new ArrayList<>();
    private final List<Processor<ServiceMethod>> serviceMethodProcessors = new ArrayList<>();

    public ProtoWalker(ProtoContext protoContext) {
        this.context = protoContext;
        this.proto = protoContext.getProto();
    }

    public static ProtoWalker newInstance(ProtoContext proto) {
        return new ProtoWalker(proto);
    }

    static <T> Processor<T> wrap(ContextlessProcessor<T> contextlessProcessor) {
        return (context1, t) -> contextlessProcessor.run(t);
    }

    public ProtoWalker onProto(Processor<Proto> processor) {
        protoProcessors.add(processor);
        return this;
    }

    public ProtoWalker onProto(ContextlessProcessor<Proto> processor) {
        protoProcessors.add(wrap(processor));
        return this;
    }

    public ProtoWalker onMessage(Processor<Message> processor) {
        messageProcessors.add(processor);
        return this;
    }

    public ProtoWalker onMessage(ContextlessProcessor<Message> processor) {
        messageProcessors.add(wrap(processor));
        return this;
    }

    public ProtoWalker onField(Processor<Field> processor) {
        fieldProcessors.add(processor);
        return this;
    }


    public ProtoWalker onField(ContextlessProcessor<Field> processor) {
        fieldProcessors.add(wrap(processor));
        return this;
    }

    public ProtoWalker onEnum(Processor<Enum> processor) {
        enumProcessors.add(processor);
        return this;
    }


    public ProtoWalker onEnum(ContextlessProcessor<Enum> processor) {
        enumProcessors.add(wrap(processor));
        return this;
    }

    public ProtoWalker onEnumConstant(Processor<EnumConstant> processor) {
        enumConstantProcessors.add(processor);
        return this;
    }


    public ProtoWalker onEnumConstant(ContextlessProcessor<EnumConstant> processor) {
        enumConstantProcessors.add(wrap(processor));
        return this;
    }

    public ProtoWalker onService(Processor<Service> processor) {
        serviceProcessors.add(processor);
        return this;
    }

    public ProtoWalker onService(ContextlessProcessor<Service> processor) {
        serviceProcessors.add(wrap(processor));
        return this;
    }

    public ProtoWalker onServiceMethod(Processor<ServiceMethod> processor) {
        serviceMethodProcessors.add(processor);
        return this;
    }

    public ProtoWalker onServiceMethod(ContextlessProcessor<ServiceMethod> processor) {
        serviceMethodProcessors.add(wrap(processor));
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
                walk(service);
            }
        }
        walk((UserTypeContainer) container);
    }

    private void walk(Service service) {
        for (Processor<ServiceMethod> serviceMethodProcessor : serviceMethodProcessors) {
            for (ServiceMethod serviceMethod : service.getMethods()) {
                serviceMethodProcessor.run(context, serviceMethod);
            }
        }
    }

    private void walk(UserTypeContainer container) {
        List<Message> messages = container.getMessages();
        for (Message message : messages) {
            for (Processor<Message> messageProcessor : messageProcessors) {
                messageProcessor.run(context, message);
                walk(message);
            }
            walk((UserTypeContainer) message);
        }
        List<Enum> enums = container.getEnums();
        for (Processor<Enum> enumProcessor : enumProcessors) {
            for (Enum anEnum : enums) {
                enumProcessor.run(context, anEnum);
                walk(anEnum);
            }
        }
    }

    private void walk(Enum anEnum) {
        for (Processor<EnumConstant> enumConstantProcessor : enumConstantProcessors) {
            for (EnumConstant enumConstant : anEnum.getConstants()) {
                enumConstantProcessor.run(context, enumConstant);
            }
        }
    }

    private void walk(Message message) {
        for (Processor<Field> fieldProcessor : fieldProcessors) {
            for (Field field : message.getFields()) {
                fieldProcessor.run(context, field);
            }
        }
    }

    @FunctionalInterface
    public interface Processor<T> {

        void run(ProtoContext context, T t);

    }

    @FunctionalInterface
    public interface ContextlessProcessor<T> {

        void run(T t);
    }
}
