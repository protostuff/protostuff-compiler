package io.protostuff.generator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

import org.junit.Before;

import io.protostuff.compiler.ParserModule;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractCompilerTest {

    protected Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(
                new ParserModule(),
                Modules.override(new CompilerModule()).with(new TestCompilerModule()));
    }

}
