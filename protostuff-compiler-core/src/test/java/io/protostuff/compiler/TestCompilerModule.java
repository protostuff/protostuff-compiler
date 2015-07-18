package io.protostuff.compiler;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.protostuff.compiler.generator.OutputStreamFactory;

import java.io.ByteArrayOutputStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class TestCompilerModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    OutputStreamFactory outputStreamFactory() {
        // dummy
        return location -> new ByteArrayOutputStream();
    }
}
