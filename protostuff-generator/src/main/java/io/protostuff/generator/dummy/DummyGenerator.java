package io.protostuff.generator.dummy;

import io.protostuff.compiler.model.Module;
import io.protostuff.generator.ProtoCompiler;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class DummyGenerator implements ProtoCompiler {

    private Module lastCompiledModule = null;

    @Override
    public void compile(Module module) {
        lastCompiledModule = module;
    }

    public Module getLastCompiledModule() {
        return lastCompiledModule;
    }

}
