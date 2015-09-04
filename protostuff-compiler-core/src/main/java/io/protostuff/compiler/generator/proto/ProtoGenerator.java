package io.protostuff.compiler.generator.proto;

import java.util.Collections;

import javax.inject.Inject;

import io.protostuff.compiler.generator.ProtoCompiler;
import io.protostuff.compiler.generator.StCompilerFactory;
import io.protostuff.compiler.model.Module;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoGenerator implements ProtoCompiler {

    public static final String GENERATOR_NAME = "proto";

    private final StCompilerFactory compilerFactory;
    private final ProtoCompiler delegate;

    @Inject
    public ProtoGenerator(StCompilerFactory compilerFactory) {
        this.compilerFactory = compilerFactory;
        // TODO initialization should be lazy - usually only one generator is used
        delegate = compilerFactory.create("io/protostuff/compiler/proto/proto3.stg", Collections.emptyMap());
    }

    @Override
    public String getName() {
        return GENERATOR_NAME;
    }

    @Override
    public void compile(Module module) {
        delegate.compile(module);
    }
}
