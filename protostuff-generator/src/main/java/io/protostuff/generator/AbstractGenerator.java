package io.protostuff.generator;

import io.protostuff.compiler.model.Module;
import org.stringtemplate.v4.AttributeRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractGenerator implements ProtoCompiler {

    private final Map<Class<?>, AttributeRenderer> rendererMap = new HashMap<>();
    private final Map<Class<?>, ObjectExtender<?>> extenderMap = new HashMap<>();

    private final StCompilerFactory compilerFactory;

    protected AbstractGenerator(StCompilerFactory compilerFactory) {
        this.compilerFactory = compilerFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> void extend(Class<T> object, String property, Function<T, ?> function) {
        ObjectExtender<T> extender = (ObjectExtender<T>) extenderMap.computeIfAbsent(object,
                aClass -> new SimpleObjectExtender<T>());
        extender.register(property, function);
    }

    @Override
    public void compile(Module module) {
        String template = module.getTemplate();
        String initializerClass = module.getInitializer();
        if (initializerClass != null && !initializerClass.isEmpty()) {
            GeneratorInitializer initializer = instantiate(initializerClass, GeneratorInitializer.class);
            initializer.init(this);
        }
        ProtoCompiler generator = compilerFactory.create(template, rendererMap, extenderMap);
        generator.compile(module);
    }

    private <T> T instantiate(final String className, final Class<T> type){
        try{
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.newInstance();
            return type.cast(instance);
        } catch(final InstantiationException | IllegalAccessException | ClassNotFoundException e){
            throw new GeneratorException("Could not instantiate " + className, e);
        }
    }
}
