package io.protostuff.compiler;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.protostuff.compiler.generator.GeneratorException;
import io.protostuff.compiler.generator.ProtoCompiler;
import io.protostuff.compiler.generator.StCompiler;
import io.protostuff.compiler.generator.StErrorListener;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CompilerModule extends AbstractModule {

    private final String templateFileName;

    public CompilerModule(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    @Override
    protected void configure() {

    }

    @Provides
    ProtoCompiler compiler() {
        STGroup group = new STGroupFile(templateFileName);
        group.setListener(new StErrorListener());
        return new StCompiler(group, location -> {
            try {
                return new FileOutputStream(location);
            } catch (FileNotFoundException e) {
                throw new GeneratorException("Could not create file: {}", location);
            }
        });
    }
}
