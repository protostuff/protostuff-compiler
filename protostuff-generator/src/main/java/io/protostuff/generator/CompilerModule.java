package io.protostuff.generator;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.inject.multibindings.MapBinder.newMapBinder;
import static org.pegdown.Extensions.ABBREVIATIONS;
import static org.pegdown.Extensions.ATXHEADERSPACE;
import static org.pegdown.Extensions.AUTOLINKS;
import static org.pegdown.Extensions.DEFINITIONS;
import static org.pegdown.Extensions.FENCED_CODE_BLOCKS;
import static org.pegdown.Extensions.FORCELISTITEMPARA;
import static org.pegdown.Extensions.HARDWRAPS;
import static org.pegdown.Extensions.RELAXEDHRULES;
import static org.pegdown.Extensions.SMARTYPANTS;
import static org.pegdown.Extensions.STRIKETHROUGH;
import static org.pegdown.Extensions.SUPPRESS_ALL_HTML;
import static org.pegdown.Extensions.TABLES;
import static org.pegdown.Extensions.TASKLISTITEMS;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.MapBinder;
import io.protostuff.generator.html.HtmlGenerator;
import io.protostuff.generator.java.JavaExtensionProvider;
import org.pegdown.PegDownProcessor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CompilerModule extends AbstractModule {

    public static final String JAVA_COMPILER_TEMPLATE = "io/protostuff/generator/java/main.stg";

    public static final String TEMPLATES_OPTION = "templates";
    public static final String EXTENSIONS_OPTION = "extensions";

    public static final String JAVA_COMPILER = "java";
    public static final String ST4_COMPILER = "st4";
    public static final String HTML_COMPILER = "html";

    private static <T> T instantiate(final String className, final Class<T> type) {
        try {
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.newInstance();
            return type.cast(instance);
        } catch (final InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new GeneratorException("Could not instantiate " + className, e);
        }
    }

    @Override
    protected void configure() {
        bind(CompilerRegistry.class);
        bind(CompilerUtils.class);
        install(new FactoryModuleBuilder()
                .implement(ProtoCompiler.class, StCompiler.class)
                .build(StCompilerFactory.class));
        install(new FactoryModuleBuilder()
                .implement(ProtoCompiler.class, ExtensibleStCompiler.class)
                .build(ExtensibleStCompilerFactory.class));
        MapBinder<String, ProtoCompiler> compilers = newMapBinder(binder(), String.class, ProtoCompiler.class);
        compilers.addBinding(HTML_COMPILER).to(HtmlGenerator.class);
        compilers.addBinding(JAVA_COMPILER).toProvider(JavaCompilerProvider.class);
        compilers.addBinding(ST4_COMPILER).toProvider(St4CompilerProvider.class);
    }

    @Provides
    PegDownProcessor pegDownProcessor() {
        return new PegDownProcessor(SMARTYPANTS
                | ABBREVIATIONS
                | AUTOLINKS
                | TABLES
                | FENCED_CODE_BLOCKS
                | DEFINITIONS
                | SUPPRESS_ALL_HTML
                | STRIKETHROUGH
                | ATXHEADERSPACE
                | FORCELISTITEMPARA
                | RELAXEDHRULES
                | TASKLISTITEMS
        );
    }

    @Provides
    OutputStreamFactory outputStreamFactory() {
        return location -> {
            try {
                Path path = Paths.get(location);
                Path dir = path.getParent();
                Files.createDirectories(dir);
                return new FileOutputStream(location);
            } catch (IOException e) {
                throw new GeneratorException("Could not create file: %s", e, location);
            }
        };
    }

    public static class JavaCompilerProvider implements Provider<ProtoCompiler> {

        private final ExtensibleStCompilerFactory factory;

        @Inject
        public JavaCompilerProvider(ExtensibleStCompilerFactory factory) {
            this.factory = factory;
        }

        @Override
        public ProtoCompiler get() {
            ExtensionProvider extensionProvider = new JavaExtensionProvider();
            return factory.create(Collections.singletonList(JAVA_COMPILER_TEMPLATE), extensionProvider);
        }
    }

    public static class St4CompilerProvider implements Provider<ProtoCompiler> {

        private final ExtensibleStCompilerFactory factory;

        @Inject
        public St4CompilerProvider(ExtensibleStCompilerFactory factory) {
            this.factory = factory;
        }

        @Override
        public ProtoCompiler get() {
            // deffer compiler instantiation until wrapper is called with
            // configured template and extension provider class
            return module -> {
                try {
                    Map<String, Object> options = module.getOptions();
                    Collection<String> templates = checkNotNull((Collection<String>) options.get(TEMPLATES_OPTION),
                            TEMPLATES_OPTION + " is not set");
                    String extProviderClass = checkNotNull((String) options.get(EXTENSIONS_OPTION),
                            EXTENSIONS_OPTION + " is not set");
                    ExtensionProvider extensionProvider = instantiate(extProviderClass, ExtensionProvider.class);
                    ProtoCompiler compiler = factory.create(templates, extensionProvider);
                    compiler.compile(module);
                } catch (Exception e) {
                    throw new GeneratorException("Could not compile module: %s, module=%s", e, e.getMessage(), module);
                }
            };
        }


    }
}
