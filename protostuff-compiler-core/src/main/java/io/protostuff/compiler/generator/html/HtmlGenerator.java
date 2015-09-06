package io.protostuff.compiler.generator.html;

import org.pegdown.PegDownProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.AttributeRenderer;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import io.protostuff.compiler.generator.CompilerUtils;
import io.protostuff.compiler.generator.ProtoCompiler;
import io.protostuff.compiler.generator.StCompilerFactory;
import io.protostuff.compiler.model.Module;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class HtmlGenerator implements ProtoCompiler {

    public static final String GENERATOR_NAME = "html";
    public static final String HTML_RESOURCE_BASE = "io/protostuff/compiler/html/";
    public static final String[] STATIC_RESOURCES = new String[]{
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
            "js/bootstrap-treeview.js",
            "js/bootstrap-treeview.min.js",
            "js/main.js",
            "css/bootstrap-theme.css",
            "css/bootstrap-theme.min.css",
            "css/bootstrap-theme.css.map",
            "css/bootstrap.min.css",
            "css/bootstrap.css.map",
            "css/bootstrap.css",
            "css/bootstrap-treeview.css",
            "css/bootstrap-treeview.min.css",
            "css/theme.css",
    };
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlGenerator.class);
    private final StCompilerFactory compilerFactory;
    private final CompilerUtils compilerUtils;

    @Inject
    public HtmlGenerator(StCompilerFactory compilerFactory, CompilerUtils compilerUtils) {
        this.compilerFactory = compilerFactory;
        this.compilerUtils = compilerUtils;
    }

    @Override
    public String getName() {
        return GENERATOR_NAME;
    }

    @Override
    public void compile(Module module) {
        // TODO generator should not be initialized for each module separately
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
        ProtoCompiler serviceGenerator = compilerFactory.create("io/protostuff/compiler/html/service.stg", rendererMap);
        serviceGenerator.compile(module);
        ProtoCompiler mainGenerator = compilerFactory.create("io/protostuff/compiler/html/main.stg", rendererMap);
        mainGenerator.compile(module);
        for (String staticResourceName : STATIC_RESOURCES) {
            String source = HTML_RESOURCE_BASE + "static/" + staticResourceName;
            //noinspection UnnecessaryLocalVariable
            String destination = module.getOutput() + "/" + staticResourceName;
            LOGGER.info("Write {}", destination);
            compilerUtils.copyResource(source, destination);
        }
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
                return processor.markdownToHtml(o.toString());
            }
            return String.valueOf(o);
        }
    }
}
