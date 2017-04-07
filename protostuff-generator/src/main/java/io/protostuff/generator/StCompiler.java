package io.protostuff.generator;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.protostuff.compiler.model.Enum;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Service;
import java.io.IOException;
import java.io.Writer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Proto compiler based on StringTemplate 4.
 * <p>
 * Given {@code templateFileName} should contain set of templates that define
 * generated code.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class StCompiler extends AbstractProtoCompiler {

    private static final String MODULE = "module";
    private static final String MODULE_COMPILER_ENABLED = "module_compiler_enabled";
    private static final String MODULE_COMPILER_TEMPLATE = "module_compiler_template";

    private static final String MODULE_COMPILER_OUTPUT = "module_compiler_output";
    private static final String PROTO = "proto";
    private static final String PROTO_COMPILER_ENABLED = "proto_compiler_enabled";
    private static final String PROTO_COMPILER_TEMPLATE = "proto_compiler_template";

    private static final String PROTO_COMPILER_OUTPUT = "proto_compiler_output";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_COMPILER_ENABLED = "message_compiler_enabled";
    private static final String MESSAGE_COMPILER_TEMPLATE = "message_compiler_template";

    private static final String MESSAGE_COMPILER_OUTPUT = "message_compiler_output";
    private static final String ENUM = "enum";
    private static final String ENUM_COMPILER_ENABLED = "enum_compiler_enabled";
    private static final String ENUM_COMPILER_TEMPLATE = "enum_compiler_template";

    private static final String ENUM_COMPILER_OUTPUT = "enum_compiler_output";
    private static final String SERVICE = "service";
    private static final String SERVICE_COMPILER_ENABLED = "service_compiler_enabled";
    private static final String SERVICE_COMPILER_TEMPLATE = "service_compiler_template";
    private static final String SERVICE_COMPILER_OUTPUT = "service_compiler_output";

    private final STGroup stGroup;

    /**
     * Create new {@link StCompiler} instance.
     */
    @Inject
    public StCompiler(OutputStreamFactory outputStreamFactory,
                      @Assisted String templateFileName) {
        super(outputStreamFactory);
        STGroup group = new STGroupFile(templateFileName);
        group.setListener(new StErrorListener());
        this.stGroup = group;
    }

    final STGroup getStGroup() {
        return stGroup;
    }

    @Override
    protected void compileModule(Module module, Writer writer) {
        compile(MODULE_COMPILER_TEMPLATE, MODULE, module, writer);
    }

    @Override
    protected void compileProto(Proto proto, Writer writer) {
        compile(PROTO_COMPILER_TEMPLATE, PROTO, proto, writer);
    }

    @Override
    protected void compileMessage(Message message, Writer writer) {
        compile(MESSAGE_COMPILER_TEMPLATE, MESSAGE, message, writer);
    }

    @Override
    protected void compileEnum(io.protostuff.compiler.model.Enum anEnum, Writer writer) {
        compile(ENUM_COMPILER_TEMPLATE, ENUM, anEnum, writer);
    }

    @Override
    protected void compileService(Service service, Writer writer) {
        compile(SERVICE_COMPILER_TEMPLATE, SERVICE, service, writer);
    }

    protected void compile(String templateName, String templateArgName, Object templateArgValue, Writer writer) {
        ST st = stGroup.getInstanceOf(templateName);
        if (st == null) {
            throw new GeneratorException("Template %s is not defined", templateName);
        }
        st.add(templateArgName, templateArgValue);
        String result = st.render();
        try {
            writer.append(result);
        } catch (IOException e) {
            throw new GeneratorException("Could not write file: %s", e.getMessage(), e);
        }
    }

    @Override
    protected boolean canProcessModule(Module module) {
        return getBoolean(MODULE_COMPILER_ENABLED, MODULE, module);
    }

    @Override
    protected boolean canProcessProto(Proto proto) {
        return getBoolean(PROTO_COMPILER_ENABLED, PROTO, proto);
    }

    @Override
    protected boolean canProcessMessage(Message message) {
        return getBoolean(MESSAGE_COMPILER_ENABLED, MESSAGE, message);
    }

    @Override
    protected boolean canProcessEnum(Enum anEnum) {
        return getBoolean(ENUM_COMPILER_ENABLED, ENUM, anEnum);
    }

    @Override
    protected boolean canProcessService(Service service) {
        return getBoolean(SERVICE_COMPILER_ENABLED, SERVICE, service);
    }

    @Override
    protected String getModuleOutputFileName(Module module) {
        return getString(MODULE_COMPILER_OUTPUT, MODULE, module);
    }

    @Override
    protected String getProtoOutputFileName(Proto proto) {
        return getString(PROTO_COMPILER_OUTPUT, PROTO, proto);
    }

    @Override
    protected String getMessageOutputFileName(Message message) {
        return getString(MESSAGE_COMPILER_OUTPUT, MESSAGE, message);
    }

    @Override
    protected String getEnumOutputFileName(Enum anEnum) {
        return getString(ENUM_COMPILER_OUTPUT, ENUM, anEnum);
    }

    @Override
    protected String getServiceOutputFileName(Service service) {
        return getString(SERVICE_COMPILER_OUTPUT, SERVICE, service);
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
