package io.protostuff.compiler.generator;

import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.UserType;
import io.protostuff.compiler.model.util.ProtoTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
    public void compiler(Proto proto) {
        try {
            ProtoTreeWalker.DEFAULT.walk(proto, (container, type) -> {
                if (canProcess(type)) {
                    String outputFileName = getOutputFileName(type);
                    Writer writer = fileWriterMap.computeIfAbsent(outputFileName, s -> {
                        OutputStream outputStream = outputStreamFactory.createStream(s);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                        return new BufferedWriter(outputStreamWriter);
                    });
                    compile(proto, type, writer);
                }
            });
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

    protected abstract void compile(Proto proto, UserType type, Writer writer);

    protected abstract boolean canProcess(UserType type);

    protected abstract String getOutputFileName(UserType type);

}
