package io.protostuff.compiler.generator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import io.protostuff.compiler.CompilerModule;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.TestCompilerModule;
import org.junit.Before;

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
