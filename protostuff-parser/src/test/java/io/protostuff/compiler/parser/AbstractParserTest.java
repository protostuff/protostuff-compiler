package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import io.protostuff.compiler.ParserModule;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public Injector injector;
    public Importer importer;

    @Before
    public void init() throws Exception {
        initLogger();
        injector = Guice.createInjector(new ParserModule());
        importer = injector.getInstance(Importer.class);
    }

    public void initLogger() {
        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
        System.setProperty("org.slf4j.simpleLogger.showThreadName", "false");
        System.setProperty("org.slf4j.simpleLogger.showShortLogName", "true");
        System.setProperty("org.slf4j.simpleLogger.levelInBrackets", "true");
    }

}
