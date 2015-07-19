package io.protostuff.compiler.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public static final String HTML_RESOURCE_BASE = "io/protostuff/compiler/html/";
    private static final Logger LOGGER = LoggerFactory.getLogger(CompilerRegistry.class);
    private final Map<String, ProtoCompiler> compilers;

    private final StCompilerFactory compilerFactory;
    private final CompilerUtils compilerUtils;

    @Inject
    public CompilerRegistry(StCompilerFactory compilerFactory, CompilerUtils compilerUtils) {
        this.compilerFactory = compilerFactory;
        this.compilerUtils = compilerUtils;
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
        return module -> {
            ProtoCompiler indexGenerator = compilerFactory.create("io/protostuff/compiler/html/index.stg");
            indexGenerator.compile(module);
            String staticResources[] = {
                    "fonts/glyphicons-halflings-regular.woff2",
                    "fonts/glyphicons-halflings-regular.svg",
                    "fonts/glyphicons-halflings-regular.woff",
                    "fonts/glyphicons-halflings-regular.eot",
                    "fonts/glyphicons-halflings-regular.ttf",
                    "js/jquery.min.js",
                    "js/jquery.js",
                    "js/jquery.min.map",
                    "js/bootstrap.min.js",
                    "js/bootstrap.js",
                    "js/npm.js",
                    "css/bootstrap-theme.css",
                    "css/bootstrap-theme.min.css",
                    "css/bootstrap.min.css",
                    "css/bootstrap.css.map",
                    "css/bootstrap.css",
                    "css/bootstrap-theme.css.map",

            };
            for (String staticResourceName : staticResources) {
                String source = HTML_RESOURCE_BASE + "static/" + staticResourceName;
                //noinspection UnnecessaryLocalVariable
                String destination = module.getOutput() + "/" + staticResourceName;
                LOGGER.info("Write {}", destination);
                compilerUtils.copyResource(source, destination);
            }
        };
    }

    @Nullable
    public ProtoCompiler findCompiler(String name) {
        return compilers.get(name);
    }

    public void registerCompiler(String name, ProtoCompiler compiler) {
        compilers.put(name, compiler);
    }
}
