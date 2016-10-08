package io.protostuff.generator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import io.protostuff.compiler.ParserModule;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractCompilerTest {

    protected Injector injector;

    @BeforeEach
    public void setUp() throws Exception {
        injector = Guice.createInjector(
                new ParserModule(),
                Modules.override(new CompilerModule()).with(new TestCompilerModule()));
    }

}
