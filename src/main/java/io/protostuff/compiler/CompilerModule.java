package io.protostuff.compiler;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.protostuff.compiler.generator.OutputStreamFactory;
import io.protostuff.compiler.generator.ProtoCompiler;
import io.protostuff.compiler.generator.StCompiler;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.OutputStream;

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
        OutputStreamFactory outputStreamFactory = new OutputStreamFactory() {

            @Override
            public OutputStream createStream(String location) {
                return System.out;
            }
        };
        STGroup group = new STGroupFile(templateFileName);
        return new StCompiler(group, outputStreamFactory);
    }
}
