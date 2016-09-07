package io.protostuff.generator;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.io.OutputStream;

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
        return new OutputStreamFactory() {
            @Override
            public OutputStream createStream(String location) {
                return System.out;
            }
        };
    }
}
