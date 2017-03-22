package io.protostuff.generator.html;

import io.protostuff.compiler.model.Module;
import io.protostuff.generator.CompilerUtils;
import io.protostuff.generator.ProtoCompiler;
import io.protostuff.generator.html.json.enumeration.JsonEnumGenerator;
import io.protostuff.generator.html.json.index.JsonIndexGenerator;
import io.protostuff.generator.html.json.message.JsonMessageGenerator;
import io.protostuff.generator.html.json.pages.JsonPageGenerator;
import io.protostuff.generator.html.json.pages.JsonPagesIndexGenerator;
import io.protostuff.generator.html.json.proto.JsonProtoGenerator;
import io.protostuff.generator.html.json.service.JsonServiceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class HtmlGenerator implements ProtoCompiler {

    public static final String PAGES = "pages";

    private static final String HTML_RESOURCE_BASE = "io/protostuff/generator/html/";
    private static final String[] STATIC_RESOURCES = new String[]{
            "index.html",
            "partials/page.html",
            "partials/type-list.html",
            "partials/type-detail.html",
            "partials/proto-detail.html",
            "partials/enum.html",
            "partials/message.html",
            "partials/service.html",
            "partials/type-ref.html",
            "partials/label-deprecated.html",
            "partials/search-result.html",
            "partials/scalar-value-types.html",
            "js/app.js",
            "js/controllers.js",
            "js/filters.js",
            "js/directives.js",
            "js/factories.js",
            "css/theme.css",
    };
    private static final String WEBJARS_RESOURCE_PREFIX = "META-INF/resources/webjars/";
    private static final String[] STATIC_LIBS = new String[]{
            "jquery/1.11.1/jquery.min.js",
            "bootstrap/3.3.5/js/bootstrap.min.js",
            "bootstrap/3.3.5/css/bootstrap.min.css",
            "bootstrap/3.3.5/css/bootstrap-theme.min.css",
            "angular-material/1.1.1/angular-material.min.css",
            "bootstrap/3.3.5/fonts/glyphicons-halflings-regular.woff2",
            "bootstrap/3.3.5/fonts/glyphicons-halflings-regular.svg",
            "bootstrap/3.3.5/fonts/glyphicons-halflings-regular.woff",
            "bootstrap/3.3.5/fonts/glyphicons-halflings-regular.eot",
            "bootstrap/3.3.5/fonts/glyphicons-halflings-regular.ttf",
            "angular-bootstrap-nav-tree-gildo/0.1.0/dist/abn_tree_directive.js",
            "angular-bootstrap-nav-tree-gildo/0.1.0/dist/abn_tree.css",
            "angularjs/1.5.8/angular.min.js",
            "angularjs/1.5.8/angular-animate.min.js",
            "angularjs/1.5.8/angular-route.min.js",
            "angularjs/1.5.8/angular-aria.min.js",
            "angularjs/1.5.8/angular-messages.min.js",
            "angularjs/1.5.8/angular-sanitize.min.js",
            "angular-material/1.1.1/angular-material.min.js",
    };
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlGenerator.class);

    private final CompilerUtils compilerUtils;
    private final JsonIndexGenerator indexGenerator;
    private final JsonEnumGenerator enumGenerator;
    private final JsonMessageGenerator messageGenerator;
    private final JsonServiceGenerator serviceGenerator;
    private final JsonProtoGenerator protoGenerator;
    private final JsonPagesIndexGenerator pagesIndexGenerator;
    private final JsonPageGenerator pageGenerator;

    @Inject
    public HtmlGenerator(CompilerUtils compilerUtils,
                         JsonIndexGenerator indexGenerator,
                         JsonEnumGenerator enumGenerator,
                         JsonMessageGenerator messageGenerator,
                         JsonServiceGenerator serviceGenerator,
                         JsonProtoGenerator protoGenerator,
                         JsonPagesIndexGenerator pagesIndexGenerator,
                         JsonPageGenerator pageGenerator) {
        this.compilerUtils = compilerUtils;
        this.indexGenerator = indexGenerator;
        this.enumGenerator = enumGenerator;
        this.messageGenerator = messageGenerator;
        this.serviceGenerator = serviceGenerator;
        this.protoGenerator = protoGenerator;
        this.pagesIndexGenerator = pagesIndexGenerator;
        this.pageGenerator = pageGenerator;
    }

    @Override
    public void compile(Module module) {
        indexGenerator.compile(module);
        enumGenerator.compile(module);
        messageGenerator.compile(module);
        serviceGenerator.compile(module);
        protoGenerator.compile(module);
        pagesIndexGenerator.compile(module);
        pageGenerator.compile(module);
        copy(HTML_RESOURCE_BASE, module.getOutput() + "/", STATIC_RESOURCES);
        copy(WEBJARS_RESOURCE_PREFIX, module.getOutput() + "/libs/", STATIC_LIBS);
    }

    private void copy(String source, String target, String[] files) {
        for (String file : files) {
            String src = source + file;
            String dst = target + file;
            LOGGER.info("Copy {}", file);
            compilerUtils.copyResource(src, dst);
        }
    }
}
