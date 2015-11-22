package io.protostuff.generator.html.json;

import com.google.common.base.Preconditions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.OutputStream;

import javax.inject.Inject;

import io.protostuff.generator.GeneratorException;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.ProtoCompiler;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractJsonGenerator implements ProtoCompiler {

    protected final OutputStreamFactory outputStreamFactory;
    protected final ObjectMapper objectMapper;

    public AbstractJsonGenerator(OutputStreamFactory outputStreamFactory) {
        this.outputStreamFactory = outputStreamFactory;
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    protected void write(String file, Object data) {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(data);
        try (OutputStream os = outputStreamFactory.createStream(file)) {
            objectMapper.writeValue(os, data);
        } catch (Exception e) {
            throw new GeneratorException("Could not write " + file, e);
        }
    }

}
