package io.protostuff.generator;

import io.protostuff.compiler.model.ImmutableModule;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.UsageIndex;
import io.protostuff.compiler.parser.ClasspathFileReader;
import io.protostuff.compiler.parser.Importer;
import io.protostuff.compiler.parser.ProtoContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class StCompilerTest extends AbstractCompilerTest {

    @Test
    @Disabled
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/messages_sample.proto");
        Proto proto = context.getProto();
        StCompilerFactory compilerFactory = injector.getInstance(StCompilerFactory.class);
        ProtoCompiler compiler = compilerFactory.create("io/protostuff/generator/proto/proto3.stg");
        Module module = ImmutableModule.builder()
                .addProtos(proto)
                .output("./")
                .usageIndex(UsageIndex.build(Collections.emptyList()))
                .build();
        compiler.compile(module);
    }
}
