package io.protostuff.generator.html.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Preconditions;
import io.protostuff.compiler.model.Module;
import io.protostuff.generator.GeneratorException;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.HtmlCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractJsonGenerator implements HtmlCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJsonGenerator.class);

    protected final OutputStreamFactory outputStreamFactory;
    protected final ObjectMapper objectMapper;

    public AbstractJsonGenerator(OutputStreamFactory outputStreamFactory) {
        this.outputStreamFactory = outputStreamFactory;
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    protected void write(Module module, String file, Object data) {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(data);
        LOGGER.info("Generate {}", file);
        String targetFile = module.getOutput() + "/" + file;
        try (OutputStream os = outputStreamFactory.createStream(targetFile)) {
            objectMapper.writeValue(os, data);
        } catch (Exception e) {
            throw new GeneratorException("Could not write " + targetFile, e);
        }
    }

}
