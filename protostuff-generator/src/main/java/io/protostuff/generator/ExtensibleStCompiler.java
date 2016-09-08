package io.protostuff.generator;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.protostuff.compiler.model.Module;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ExtensibleStCompiler implements ProtoCompiler {

    private final StCompilerFactory compilerFactory;
    private final List<StCompiler> compilers;


    @Inject
    protected ExtensibleStCompiler(StCompilerFactory compilerFactory,
                                   @Assisted Collection<String> templates,
                                   @Assisted ExtensionProvider extensionProvider) {
        this.compilerFactory = compilerFactory;
        this.compilers = new ArrayList<StCompiler>();
        for (String template : templates) {
            StCompiler compiler = (StCompiler) this.compilerFactory.create(template);
            STGroup group = compiler.getStGroup();
            addRenderExtensions(group, extensionProvider);
            addPropertyExtensions(group, extensionProvider);
            compilers.add(compiler);
        }
    }

    private void addPropertyExtensions(STGroup group, ExtensionProvider extensionProvider) {
        Map<Class<?>, PropertyProvider<?>> extenderMap = extensionProvider.propertyProviders();
        for (Map.Entry<Class<?>, PropertyProvider<?>> entry : extenderMap.entrySet()) {
            Class<?> objectClass = entry.getKey();
            final PropertyProvider<Object> extender = (PropertyProvider<Object>) entry.getValue();
            group.registerModelAdaptor(objectClass, new ObjectModelAdaptor() {
                @Override
                public synchronized Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName) throws STNoSuchPropertyException {
                    if (extender.hasProperty(propertyName)) {
                        return extender.getProperty(o, propertyName);
                    }
                    return super.getProperty(interp, self, o, property, propertyName);
                }
            });
        }
    }

    private void addRenderExtensions(STGroup group, ExtensionProvider extensionProvider) {
        Map<Class<?>, AttributeRenderer> attributeRendererMap = extensionProvider.attributeRenderers();
        for (Map.Entry<Class<?>, AttributeRenderer> entry : attributeRendererMap.entrySet()) {
            group.registerRenderer(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void compile(Module module) {
        for (StCompiler compiler : compilers) {
            compiler.compile(module);
        }
    }

}
