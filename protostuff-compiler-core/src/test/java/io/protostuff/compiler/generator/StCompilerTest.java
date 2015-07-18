package io.protostuff.compiler.generator;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.parser.Importer;
import io.protostuff.compiler.parser.ProtoContext;
import org.junit.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class StCompilerTest extends AbstractCompilerTest {

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("protostuff_unittest/messages_sample.proto");
        Proto proto = context.getProto();
        ProtoCompilerFactory compilerFactory = injector.getInstance(ProtoCompilerFactory.class);
        ProtoCompiler compiler = compilerFactory.create("io/protostuff/compiler/proto/proto3.stg");
        compiler.compile(new Module(proto));
    }
}
