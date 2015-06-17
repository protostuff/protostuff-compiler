package io.protostuff.compiler.generator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.CompilerModule;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.parser.Importer;
import io.protostuff.compiler.parser.ProtoContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class StCompilerIT {
    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(
                new ParserModule(),
                new CompilerModule("io/protostuff/compiler/proto/proto3.stg"));
    }

    @Test
    public void test() throws Exception {
        Importer importer = injector.getInstance(Importer.class);
        ProtoContext context = importer.importFile("protostuff_unittest/messages_sample.proto");
        Proto proto = context.getProto();
        ProtoCompiler compiler = injector.getInstance(ProtoCompiler.class);
        compiler.compile(new Module(proto));
    }
}
