package io.protostuff.generator;

import com.google.inject.Injector;
import io.protostuff.compiler.model.ImmutableModuleConfiguration;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.generator.dummy.DummyGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class ProtostuffCompilerTest {

    @Test
    void compile() {
        ProtostuffCompiler compiler = new ProtostuffCompiler();
        compiler.compile(ImmutableModuleConfiguration.builder()
                .name("none")
                .addProtoFiles("protostuff_unittest/messages_sample.proto")
                .generator(CompilerModule.DUMMY_COMPILER)
                .output("none")
                .build());
        Injector injector = compiler.injector;
        CompilerRegistry registry = injector.getInstance(CompilerRegistry.class);
        DummyGenerator generator = (DummyGenerator) registry.findCompiler(CompilerModule.DUMMY_COMPILER);
        Assertions.assertNotNull(generator);
        Module compiledModule = generator.getLastCompiledModule();
        Assertions.assertNotNull(compiledModule);
        Proto proto = compiledModule.getProtos().get(0);
        Assertions.assertEquals("A", proto.getMessage("A").getName());
    }

}