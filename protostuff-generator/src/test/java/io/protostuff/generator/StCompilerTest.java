package io.protostuff.generator;

import org.junit.Test;

import java.util.Collections;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.parser.ClasspathFileReader;
import io.protostuff.compiler.parser.Importer;
import io.protostuff.compiler.parser.ProtoContext;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class StCompilerTest extends AbstractCompilerTest {

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/messages_sample.proto");
        Proto proto = context.getProto();
        StCompilerFactory compilerFactory = injector.getInstance(StCompilerFactory.class);
        ProtoCompiler compiler = compilerFactory.create("io/protostuff/generator/proto/proto3.stg", Collections.emptyMap(), Collections.emptyMap());
        Module module = new Module(proto);
        module.setOutput("./");
        compiler.compile(module);
    }
}
