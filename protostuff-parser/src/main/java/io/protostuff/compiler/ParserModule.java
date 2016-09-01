package io.protostuff.compiler;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import io.protostuff.compiler.parser.*;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.BailErrorStrategy;

import static io.protostuff.compiler.parser.DefaultDescriptorProtoProvider.DESCRIPTOR_PROTO;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ParserModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Importer.class).to(ImporterImpl.class);
        bind(FileDescriptorLoader.class).to(FileDescriptorLoaderImpl.class);
        bind(ANTLRErrorListener.class).to(ParseErrorLogger.class);
        bind(ANTLRErrorStrategy.class).to(BailErrorStrategy.class);
        bind(ProtoContext.class)
                .annotatedWith(Names.named(DESCRIPTOR_PROTO))
                .toProvider(DefaultDescriptorProtoProvider.class);

        Multibinder<ProtoContextPostProcessor> postProcessors = Multibinder
                .newSetBinder(binder(), ProtoContextPostProcessor.class);
        postProcessors.addBinding().to(ImportsPostProcessor.class);
        postProcessors.addBinding().to(TypeRegistratorPostProcessor.class);
        postProcessors.addBinding().to(TypeResolverPostProcessor.class);
        postProcessors.addBinding().to(ExtensionRegistratorPostProcessor.class);
        postProcessors.addBinding().to(OptionsPostProcessor.class);
        postProcessors.addBinding().to(UserTypeValidationPostProcessor.class);

        install(new FactoryModuleBuilder()
                .implement(FileReader.class, ProtoFileReader.class)
                .build(FileReaderFactory.class));
    }

}
