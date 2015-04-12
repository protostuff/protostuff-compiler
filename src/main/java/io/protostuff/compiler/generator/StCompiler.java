package io.protostuff.compiler.generator;

import io.protostuff.compiler.model.*;
import io.protostuff.compiler.model.Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class StCompiler extends AbstractProtoCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StCompiler.class);

    private final STGroup stGroup;
    public StCompiler(STGroup group, OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
        this.stGroup = group;
    }

    @Override
    protected void compile(Proto proto, Writer writer) {
        String stName = "proto_compiler_template";
        ST st = stGroup.getInstanceOf(stName);
        if (st == null) {
            throw new GeneratorException("Template %s is not defined", stName);
        }
        st.add("proto", proto);
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
    protected boolean canProcess(Proto proto) {
        return getBoolean("proto_compiler_enabled", "proto", proto);
    }

    @Override
    protected boolean canProcess(Message message) {
        return getBoolean("message_compiler_enabled", "message", message);
    }

    @Override
    protected boolean canProcess(Enum anEnum) {
        return getBoolean("enum_compiler_enabled", "enum", anEnum);
    }

    @Override
    protected String getOutputFileName(Proto proto) {
        return getString("proto_compiler_output", "proto", proto);
    }

    @Override
    protected String getOutputFileName(Message message) {
        return getString("message_compiler_output", "message", message);
    }

    @Override
    protected String getOutputFileName(Enum anEnum) {
        return getString("enum_compiler_output", "enum", anEnum);
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
