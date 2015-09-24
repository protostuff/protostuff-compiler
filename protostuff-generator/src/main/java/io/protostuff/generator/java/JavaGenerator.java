package io.protostuff.generator.java;

import java.util.Collections;

import javax.inject.Inject;

import io.protostuff.compiler.model.Module;
import io.protostuff.generator.ProtoCompiler;
import io.protostuff.generator.StCompilerFactory;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JavaGenerator implements ProtoCompiler {
    public static final String GENERATOR_NAME = "java";

    private final StCompilerFactory compilerFactory;
    private final ProtoCompiler delegate;

    @Inject
    public JavaGenerator(StCompilerFactory compilerFactory) {
        this.compilerFactory = compilerFactory;
        // TODO initialization should be lazy - usually only one generator is used
        delegate = compilerFactory.create("io/protostuff/generator/java/message.stg", Collections.emptyMap());
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
