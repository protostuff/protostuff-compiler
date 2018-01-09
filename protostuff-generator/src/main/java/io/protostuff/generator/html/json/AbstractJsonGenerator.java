package io.protostuff.generator.html.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.base.Preconditions;
import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.DynamicMessage.Value;
import io.protostuff.compiler.model.Module;
import io.protostuff.generator.GeneratorException;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.HtmlCompiler;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for JSON generators.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractJsonGenerator implements HtmlCompiler {
    /**
     * Jackson serializer for {@link DynamicMessage.Value}.
     */
    public static class ValueSerializer extends StdSerializer<DynamicMessage.Value> {
        public ValueSerializer() {
            this(null);
        }
        
        public ValueSerializer(Class<DynamicMessage.Value> t) {
            super(t);
        }
        
        @Override
        public void serialize(Value value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            switch (value.getType()) {
                case BOOLEAN:
                    gen.writeBoolean(value.getBoolean());
                    break;
                case INTEGER:
                    gen.writeNumber(value.getInt64());
                    break;
                case FLOAT:
                    gen.writeNumber(value.getDouble());
                    break;
                case STRING:
                    gen.writeString(value.getString());
                    break;
                case ENUM:
                    gen.writeString(value.getEnumName());
                    break;
                case MESSAGE:
                    gen.writeObject(value.getMessage());
                    break;
                default:
                    throw new IllegalStateException(String.valueOf(value.getType()));
            }
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJsonGenerator.class);

    protected final OutputStreamFactory outputStreamFactory;
    protected final ObjectMapper objectMapper;

    /**
     * Create new JSON generator instance.
     */
    public AbstractJsonGenerator(OutputStreamFactory outputStreamFactory) {
        this.outputStreamFactory = outputStreamFactory;
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Value.class, new ValueSerializer());
        objectMapper.registerModule(module);    
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
