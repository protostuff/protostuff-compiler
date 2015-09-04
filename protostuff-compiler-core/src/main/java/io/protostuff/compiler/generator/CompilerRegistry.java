package io.protostuff.compiler.generator;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CompilerRegistry {

    private final Map<String, ProtoCompiler> compilers;

    @Inject
    public CompilerRegistry(Map<String, ProtoCompiler> compilers) {
        this.compilers = compilers;
    }

    @Nullable
    public ProtoCompiler findCompiler(String name) {
        return compilers.get(name);
    }

}
