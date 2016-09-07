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
            String moduleOutput = module.getOutput();
            if (canProcessModule(module)) {
                String outputFileName = getModuleOutputFileName(module);
                Writer writer = null;
                try {
                    writer = getWriter(moduleOutput, outputFileName);
                    compileModule(module, writer);
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (Exception e) {
                            LOGGER.error("Could not close {}", outputFileName);
                        }
                    }
                }
            }
            for (Proto proto : module.getProtos()) {
                if (canProcessProto(proto)) {
                    String outputFileName = getProtoOutputFileName(proto);
                    Writer writer = null;
                    try {
                        writer = getWriter(moduleOutput, outputFileName);
                        compileProto(proto, writer);
                    } finally {
                        if (writer != null) {
                            try {
                                writer.close();
                            } catch (Exception e) {
                                LOGGER.error("Could not close {}", outputFileName);
                            }
                        }
                    }
                }
                for (Service service : proto.getServices()) {
                    if (canProcessService(service)) {
                        String outputFileName = getServiceOutputFileName(service);
                        Writer writer = null;
                        try {
                            writer = getWriter(moduleOutput, outputFileName);
                            compileService(service, writer);
                        } finally {
                            if (writer != null) {
                                try {
                                    writer.close();
                                } catch (Exception e) {
                                    LOGGER.error("Could not close {}", outputFileName);
                                }
                            }
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
        String basedir = module.getOutput();
        for (Message message : messages) {
            if (canProcessMessage(message)) {
                String outputFileName = getMessageOutputFileName(message);
                Writer writer = null;
                try {
                    writer = getWriter(basedir, outputFileName);
                    compileMessage(message, writer);
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (Exception e) {
                            LOGGER.error("Could not close {}", outputFileName);
                        }
                    }
                }
            }
            // process nested messages and enums
            processUserTypes(module, message);
        }
        for (Enum anEnum : enums) {
            if (canProcessEnum(anEnum)) {
                String outputFileName = getEnumOutputFileName(anEnum);
                Writer writer = null;
                try {
                    writer = getWriter(basedir, outputFileName);
                    compileEnum(anEnum, writer);
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (Exception e) {
                            LOGGER.error("Could not close {}", outputFileName);
                        }
                    }
                }
            }
        }
    }

    protected String appendBasedir(String basedir, String relativeFilename) {
        if (basedir.charAt(basedir.length() - 1) == getFolderSeparator()) {
            return basedir + relativeFilename;
        } else {
            return basedir + getFolderSeparator() + relativeFilename;
        }
    }

    protected char getFolderSeparator() {
        return File.separatorChar;
    }

    private Writer getWriter(String basedir, String outputFileName) {
        LOGGER.info("Generate {}", outputFileName);
        String fullFileLocation = appendBasedir(basedir, outputFileName);
        OutputStream outputStream = outputStreamFactory.createStream(fullFileLocation);
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

    protected abstract String getModuleOutputFileName(Module module);

    protected abstract String getProtoOutputFileName(Proto proto);

    protected abstract String getMessageOutputFileName(Message message);

    protected abstract String getEnumOutputFileName(Enum anEnum);

    protected abstract String getServiceOutputFileName(Service service);

}
