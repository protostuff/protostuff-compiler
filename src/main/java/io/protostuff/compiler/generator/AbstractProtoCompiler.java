package io.protostuff.compiler.generator;

import io.protostuff.compiler.model.*;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.util.ProtoTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractProtoCompiler implements ProtoCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProtoCompiler.class);

    private final OutputStreamFactory outputStreamFactory;

    private final ConcurrentMap<String, Writer> fileWriterMap = new ConcurrentHashMap<>();

    public AbstractProtoCompiler(OutputStreamFactory outputStreamFactory) {
        this.outputStreamFactory = outputStreamFactory;
    }

    @Override
    public void compile(Proto proto) {
        try {
            List<Message> messages = proto.getMessages();
            List<io.protostuff.compiler.model.Enum> enums = proto.getEnums();
            if (canProcess(proto)) {
                LOGGER.info("Compile proto: {}", proto.getName());
                String outputFileName = getOutputFileName(proto);
                Writer writer = getWriter(outputFileName);
                compile(proto, writer);
            }
            for (Message message : messages) {
                if (canProcess(message)) {
                    LOGGER.info("Compile message: {}", message.getName());
                    String outputFileName = getOutputFileName(message);
                    Writer writer = getWriter(outputFileName);
                    compile(message, writer);
                }
            }
            for (Enum anEnum : enums) {
                if (canProcess(anEnum)) {
                    LOGGER.info("Compile enum: {}", anEnum.getName());
                    String outputFileName = getOutputFileName(anEnum);
                    Writer writer = getWriter(outputFileName);
                    compile(anEnum, writer);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Can not compile {}", proto.getName(), e);
        } finally {
            fileWriterMap.entrySet().forEach(entry -> {
                String fileName = entry.getKey();
                Writer writer = entry.getValue();
                try {
                    writer.close();
                } catch (IOException e) {
                    LOGGER.error("Could not close file: {}", fileName, e);
                }
            });
        }
    }

    private Writer getWriter(String outputFileName) {
        return fileWriterMap.computeIfAbsent(outputFileName, s -> {
                            OutputStream outputStream = outputStreamFactory.createStream(s);
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                            return new BufferedWriter(outputStreamWriter);
                        });
    }

    protected abstract void compile(Proto proto, Writer writer);
    protected abstract void compile(Message message, Writer writer);
    protected abstract void compile(Enum anEnum, Writer writer);

    protected abstract boolean canProcess(Proto proto);
    protected abstract boolean canProcess(Message message);
    protected abstract boolean canProcess(Enum anEnum);

    protected abstract String getOutputFileName(Proto proto);
    protected abstract String getOutputFileName(Message message);
    protected abstract String getOutputFileName(Enum anEnum);

}
