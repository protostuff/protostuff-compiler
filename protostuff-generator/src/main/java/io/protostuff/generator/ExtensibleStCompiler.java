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

import java.util.Map;

public class ExtensibleStCompiler implements ProtoCompiler {

    private final StCompilerFactory compilerFactory;
    private final StCompiler stCompiler;


    @Inject
    protected ExtensibleStCompiler(StCompilerFactory compilerFactory,
                                   @Assisted String templateFileName,
                                   @Assisted ExtensionProvider extensionProvider) {
        this.compilerFactory = compilerFactory;
        stCompiler = (StCompiler) this.compilerFactory.create(templateFileName);
        STGroup group = stCompiler.getStGroup();
        addRenderExtensions(group, extensionProvider);
        addPropertyExtensions(group, extensionProvider);
    }

    private void addPropertyExtensions(STGroup group, ExtensionProvider extensionProvider) {
        Map<Class<?>, PropertyProvider<?>> extenderMap = extensionProvider.propertyProviders();
        for (Map.Entry<Class<?>, PropertyProvider<?>> entry : extenderMap.entrySet()) {
            Class<?> objectClass = entry.getKey();
            PropertyProvider<Object> extender = (PropertyProvider<Object>) entry.getValue();
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
        stCompiler.compile(module);
    }

}
