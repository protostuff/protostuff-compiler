package io.protostuff.compiler;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import io.protostuff.compiler.generator.CompilerRegistry;
import io.protostuff.compiler.generator.CompilerUtils;
import io.protostuff.compiler.generator.GeneratorException;
import io.protostuff.compiler.generator.OutputStreamFactory;
import io.protostuff.compiler.generator.ProtoCompiler;
import io.protostuff.compiler.generator.StCompiler;
import io.protostuff.compiler.generator.StCompilerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
