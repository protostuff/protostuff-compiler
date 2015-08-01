package io.protostuff.compiler.generator;

import com.google.common.base.Joiner;
import org.pegdown.PegDownProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.AttributeRenderer;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
        return compilerFactory.create("io/protostuff/compiler/proto/proto3.stg", Collections.emptyMap());
    }

    public static class MarkdownRenderer implements AttributeRenderer {

        @Override
        @SuppressWarnings("unchecked")
        public String toString(Object o, String s, Locale locale) {
            if ("markdown2html".equals(s)) {
                if (o == null) {
                    return "";
                }
                PegDownProcessor processor = new PegDownProcessor();
                if (o instanceof List) {
                    List<String> lines = (List<String>) o;
                    String source = Joiner.on('\n').join(lines);
                    return processor.markdownToHtml(source);
                }
                return processor.markdownToHtml(o.toString());
            }
            return String.valueOf(o);
        }
    }
    private ProtoCompiler createHtmlCompiler() {
        return module -> {
            MarkdownRenderer renderer = new MarkdownRenderer();
            Map<Class<?>, AttributeRenderer> rendererMap = new HashMap<>();
            rendererMap.put(List.class, renderer);
            rendererMap.put(String.class, renderer);
            ProtoCompiler indexGenerator = compilerFactory.create("io/protostuff/compiler/html/index.stg", rendererMap);
            indexGenerator.compile(module);
            ProtoCompiler messageGenerator = compilerFactory.create("io/protostuff/compiler/html/message.stg", rendererMap);
            messageGenerator.compile(module);
            ProtoCompiler enumGenerator = compilerFactory.create("io/protostuff/compiler/html/enum.stg", rendererMap);
            enumGenerator.compile(module);
            ProtoCompiler mainGenerator = compilerFactory.create("io/protostuff/compiler/html/main.stg", rendererMap);
            mainGenerator.compile(module);
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
                    "css/theme.css",
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
