package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractParserTest {

    public Injector injector;
    public Importer importer;

    @BeforeEach
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
