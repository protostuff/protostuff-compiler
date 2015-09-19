package io.protostuff.generator;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.MapBinder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.protostuff.generator.html.HtmlGenerator;
import io.protostuff.generator.proto.ProtoGenerator;

import static com.google.inject.multibindings.MapBinder.newMapBinder;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CompilerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CompilerRegistry.class);
        bind(CompilerUtils.class);
        install(new FactoryModuleBuilder()
                .implement(ProtoCompiler.class, StCompiler.class)
                .build(StCompilerFactory.class));
        MapBinder<String, ProtoCompiler> compilers = newMapBinder(binder(), String.class, ProtoCompiler.class);
        compilers.addBinding(HtmlGenerator.GENERATOR_NAME).to(HtmlGenerator.class);
        compilers.addBinding(ProtoGenerator.GENERATOR_NAME).to(ProtoGenerator.class);
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
}
