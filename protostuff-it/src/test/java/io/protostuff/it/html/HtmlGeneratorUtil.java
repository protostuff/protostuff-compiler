package io.protostuff.it.html;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class HtmlGeneratorUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new GuavaModule());
    }

    private HtmlGeneratorUtil() {
    }

    public static <T> T read(Class<T> type, String location) {
        try {
            URL url = Resources.getResource(location);
            byte[] bytes = Resources.toByteArray(url);
            ObjectReader reader = mapper.reader().forType(type);
            return reader.readValue(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Could not read resource: " + location, e);
        }
    }
}
