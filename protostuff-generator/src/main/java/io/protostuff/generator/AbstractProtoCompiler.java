package io.protostuff.generator;

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
        String moduleOutput = module.getOutput();
        processModule(module, moduleOutput);
        for (Proto proto : module.getProtos()) {
            processProto(moduleOutput, proto);
            for (Service service : proto.getServices()) {
                processService(moduleOutput, service);
            }
            processUserTypes(module, proto);
        }
    }

    private void processService(String moduleOutput, Service service) {
        if (!canProcessService(service)) {
            return;
        }
        String outputFileName = getServiceOutputFileName(service);
        try (Writer writer = getWriter(moduleOutput, outputFileName)) {
            compileService(service, writer);
        } catch (IOException e) {
            throw writerException(outputFileName, e);
        }
    }

    private void processProto(String moduleOutput, Proto proto) {
        if (!canProcessProto(proto)) {
            return;
        }
        String outputFileName = getProtoOutputFileName(proto);
        try (Writer writer = getWriter(moduleOutput, outputFileName)) {
            compileProto(proto, writer);
        } catch (IOException e) {
            throw writerException(outputFileName, e);
        }
    }

    private void processModule(Module module, String moduleOutput) {
        if (!canProcessModule(module)) {
            return;
        }
        String outputFileName = getModuleOutputFileName(module);
        try (Writer writer = getWriter(moduleOutput, outputFileName)) {
            compileModule(module, writer);
        } catch (IOException e) {
            throw writerException(outputFileName, e);
        }
    }

    private void processUserTypes(Module module, UserTypeContainer container) {
        List<Message> messages = container.getMessages();
        List<io.protostuff.compiler.model.Enum> enums = container.getEnums();
        String basedir = module.getOutput();
        for (Message message : messages) {
            processMessage(basedir, message);
            // process nested messages and enums
            processUserTypes(module, message);
        }
        for (Enum anEnum : enums) {
            processEnum(basedir, anEnum);
        }
    }

    private void processEnum(String basedir, Enum anEnum) {
        if (!canProcessEnum(anEnum)) {
            return;
        }
        String outputFileName = getEnumOutputFileName(anEnum);
        try (Writer writer = getWriter(basedir, outputFileName)) {
            compileEnum(anEnum, writer);
        } catch (IOException e) {
            throw writerException(outputFileName, e);
        }
    }

    private void processMessage(String basedir, Message message) {
        if (!canProcessMessage(message)) {
            return;
        }
        String outputFileName = getMessageOutputFileName(message);
        try (Writer writer = getWriter(basedir, outputFileName)) {
            compileMessage(message, writer);
        } catch (IOException e) {
            throw writerException(outputFileName, e);
        }
    }

    private GeneratorException writerException(String outputFileName, IOException e) {
        return new GeneratorException("Could not write %s", outputFileName, e);
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
