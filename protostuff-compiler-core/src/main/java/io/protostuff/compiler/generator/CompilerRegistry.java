package io.protostuff.compiler.generator;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CompilerRegistry {

    public static final String PROTO3 = "proto3";
    public static final String HTML = "html";

    private final Map<String, ProtoCompiler> compilers;

    private final StCompilerFactory compilerFactory;

    @Inject
    public CompilerRegistry(StCompilerFactory compilerFactory) {
        this.compilerFactory = compilerFactory;
        compilers = new HashMap<>();
        registerStandardCompilers();
    }

    private void registerStandardCompilers() {
        registerCompiler(PROTO3, createProto3Compiler());
        registerCompiler(HTML, createHtmlCompiler());
    }

    private ProtoCompiler createProto3Compiler() {
        return compilerFactory.create("io/protostuff/compiler/proto/proto3.stg");
    }

    private ProtoCompiler createHtmlCompiler() {
        return compilerFactory.create("io/protostuff/compiler/html/index.stg");
    }

    @Nullable
    public ProtoCompiler findCompiler(String name) {
        return compilers.get(name);
    }

    public void registerCompiler(String name, ProtoCompiler compiler) {
        compilers.put(name, compiler);
    }
}
