package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Extension;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CustomOptionsIT extends AbstractParserTest {

    @Test
    public void test() throws Exception {
        importer.importFile("protobuf_unittest/unittest_custom_options.proto");
    }

    @Test
    public void testValidator() throws Exception {
        importer.importFile("protostuff_unittest/options_sample.proto");
    }
}
