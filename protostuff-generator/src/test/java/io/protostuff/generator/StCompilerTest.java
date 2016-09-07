package io.protostuff.generator;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.parser.ClasspathFileReader;
import io.protostuff.compiler.parser.Importer;
import io.protostuff.compiler.parser.ProtoContext;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class StCompilerTest extends AbstractCompilerTest {

    @Test
    @Ignore
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/messages_sample.proto");
        Proto proto = context.getProto();
        StCompilerFactory compilerFactory = injector.getInstance(StCompilerFactory.class);
        ProtoCompiler compiler = compilerFactory.create("io/protostuff/generator/proto/proto3.stg");
        Module module = new Module();
        module.setProtos(Collections.singletonList(proto));
        module.setOutput("./");
        compiler.compile(module);
    }
}
