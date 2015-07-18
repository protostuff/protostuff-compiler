package io.protostuff.compiler.generator;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class StCompiler extends AbstractProtoCompiler {

    public static final String MODULE = "module";
    public static final String MODULE_COMPILER_ENABLED = "module_compiler_enabled";
    public static final String MODULE_COMPILER_TEMPLATE = "module_compiler_template";
    public static final String MODULE_COMPILER_OUTPUT = "module_compiler_output";

    public static final String PROTO = "proto";
    public static final String PROTO_COMPILER_ENABLED = "proto_compiler_enabled";
    public static final String PROTO_COMPILER_TEMPLATE = "proto_compiler_template";
    public static final String PROTO_COMPILER_OUTPUT = "proto_compiler_output";

    public static final String MESSAGE = "message";
    public static final String MESSAGE_COMPILER_ENABLED = "message_compiler_enabled";
    public static final String MESSAGE_COMPILER_OUTPUT = "message_compiler_output";

    public static final String ENUM = "enum";
    public static final String ENUM_COMPILER_ENABLED = "enum_compiler_enabled";
    public static final String ENUM_COMPILER_OUTPUT = "enum_compiler_output";

    private static final Logger LOGGER = LoggerFactory.getLogger(StCompiler.class);
    private final STGroup stGroup;

    @Inject
    public StCompiler(OutputStreamFactory outputStreamFactory, @Assisted String templateFileName) {
        super(outputStreamFactory);
        STGroup group = new STGroupFile(templateFileName);
        group.setListener(new StErrorListener());
        this.stGroup = group;
    }

    @Override
    protected void compile(Module module, Writer writer) {
        String stName = MODULE_COMPILER_TEMPLATE;
        ST st = stGroup.getInstanceOf(stName);
        if (st == null) {
            throw new GeneratorException("Template %s is not defined", stName);
        }
        st.add(MODULE, module);
        String result = st.render();
        try {
            writer.append(result);
            writer.flush();
        } catch (IOException e) {
            throw new GeneratorException("Can not write file: %s", e.getMessage());
        }
    }

    @Override
    protected void compile(Proto proto, Writer writer) {
        String stName = PROTO_COMPILER_TEMPLATE;
        ST st = stGroup.getInstanceOf(stName);
        if (st == null) {
            throw new GeneratorException("Template %s is not defined", stName);
        }
        st.add(PROTO, proto);
        String result = st.render();
        try {
            writer.append(result);
        } catch (IOException e) {
            throw new GeneratorException("Can not write file: %s", e.getMessage());
        }
    }

    @Override
    protected void compile(Message message, Writer writer) {

    }

    @Override
    protected void compile(io.protostuff.compiler.model.Enum anEnum, Writer writer) {

    }

    @Override
    protected boolean canProcess(Module module) {
        return getBoolean(MODULE_COMPILER_ENABLED, MODULE, module);
    }

    @Override
    protected boolean canProcess(Proto proto) {
        return getBoolean(PROTO_COMPILER_ENABLED, PROTO, proto);
    }

    @Override
    protected boolean canProcess(Message message) {
        return getBoolean(MESSAGE_COMPILER_ENABLED, MESSAGE, message);
    }

    @Override
    protected boolean canProcess(Enum anEnum) {
        return getBoolean(ENUM_COMPILER_ENABLED, ENUM, anEnum);
    }

    @Override
    protected String getOutputFileName(Module module) {
        return getString(MODULE_COMPILER_OUTPUT, MODULE, module);
    }

    @Override
    protected String getOutputFileName(Proto proto) {
        return getString(PROTO_COMPILER_OUTPUT, PROTO, proto);
    }

    @Override
    protected String getOutputFileName(Message message) {
        return getString(MESSAGE_COMPILER_OUTPUT, MESSAGE, message);
    }

    @Override
    protected String getOutputFileName(Enum anEnum) {
        return getString(ENUM_COMPILER_OUTPUT, ENUM, anEnum);
    }

    private boolean getBoolean(String stName, String arg, Object value) {
        String templateOutput = getString(stName, arg, value);
        return Boolean.parseBoolean(templateOutput);
    }

    private String getString(String stName, String arg, Object value) {
        ST st = stGroup.getInstanceOf(stName);
        if (st == null) {
            throw new GeneratorException("Template %s is not defined", stName);
        }
        st.add(arg, value);
        return st.render();
    }

}
