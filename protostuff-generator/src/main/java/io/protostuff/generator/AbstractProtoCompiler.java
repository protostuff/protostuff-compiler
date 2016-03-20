package io.protostuff.generator;

import com.google.common.base.Throwables;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractProtoCompiler implements ProtoCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProtoCompiler.class);

    private final OutputStreamFactory outputStreamFactory;

    public AbstractProtoCompiler(OutputStreamFactory outputStreamFactory) {
        this.outputStreamFactory = outputStreamFactory;
    }

    @Override
    public void compile(Module module) {
        try {
            if (canProcessModule(module)) {
                String outputFileName = getModuleOutputFileName(module.getOutput(), module);
                LOGGER.info("Write {}", outputFileName);
                try (Writer writer = getWriter(outputFileName)) {
                    compileModule(module, writer);
                }
            }
            for (Proto proto : module.getProtos()) {
                if (canProcessProto(proto)) {
                    String outputFileName = getProtoOutputFileName(module.getOutput(), proto);
                    LOGGER.info("Write {}", outputFileName);
                    try (Writer writer = getWriter(outputFileName)) {
                        compileProto(proto, writer);
                    }
                }
                for (Service service : proto.getServices()) {
                    if (canProcessService(service)) {
                        String outputFileName = getServiceOutputFileName(module.getOutput(), service);
                        LOGGER.info("Write {}", outputFileName);
                        try (Writer writer = getWriter(outputFileName)) {
                            compileService(service, writer);
                        }
                    }
                }
                processUserTypes(module, proto);
            }
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private void processUserTypes(Module module, UserTypeContainer container) throws IOException {
        List<Message> messages = container.getMessages();
        List<io.protostuff.compiler.model.Enum> enums = container.getEnums();

        for (Message message : messages) {
            if (canProcessMessage(message)) {
                String outputFileName = getMessageOutputFileName(module.getOutput(), message);
                LOGGER.info("Write {}", outputFileName);
                try (Writer writer = getWriter(outputFileName)) {
                    compileMessage(message, writer);
                }
            }
            // process nested messages and enums
            processUserTypes(module, message);
        }
        for (Enum anEnum : enums) {
            if (canProcessEnum(anEnum)) {
                String outputFileName = getEnumOutputFileName(module.getOutput(), anEnum);
                LOGGER.info("Write {}", outputFileName);
                try (Writer writer = getWriter(outputFileName)) {
                    compileEnum(anEnum, writer);
                }
            }
        }
    }

    private Writer getWriter(String outputFileName) {
        OutputStream outputStream = outputStreamFactory.createStream(outputFileName);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        return new BufferedWriter(outputStreamWriter);
    }

    protected abstract void compileModule(Module module, Writer writer);

    protected abstract void compileProto(Proto proto, Writer writer);

    protected abstract void compileMessage(Message message, Writer writer);

    protected abstract void compileEnum(Enum anEnum, Writer writer);

    protected abstract void compileService(Service service, Writer writer);

    protected abstract boolean canProcessModule(Module module);

    protected abstract boolean canProcessProto(Proto proto);

    protected abstract boolean canProcessMessage(Message message);

    protected abstract boolean canProcessEnum(Enum anEnum);

    protected abstract boolean canProcessService(Service service);

    protected abstract String getModuleOutputFileName(String basedir, Module module);

    protected abstract String getProtoOutputFileName(String basedir, Proto proto);

    protected abstract String getMessageOutputFileName(String basedir, Message message);

    protected abstract String getEnumOutputFileName(String basedir, Enum anEnum);

    protected abstract String getServiceOutputFileName(String basedir, Service service);

}
