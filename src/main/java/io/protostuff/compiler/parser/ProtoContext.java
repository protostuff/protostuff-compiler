package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.*;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoContext implements ExtensionRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoContext.class);

    private final Map<String, Type> symbolTable;
    private final Deque<Object> declarationStack;
    private final Proto proto;
    private final ExtensionRegistry localExtensionRegistry;
    private final List<ProtoContext> imports;
    private final List<ProtoContext> publicImports;

    private boolean initialized;

    public ProtoContext(String filename) {
        localExtensionRegistry = new LocalExtensionRegistry();
        symbolTable = new HashMap<>();
        declarationStack = new ArrayDeque<>();
        imports = new ArrayList<>();
        publicImports = new ArrayList<>();
        proto = new Proto();
        proto.setFilename(filename);
        String shortFilename = FilenameUtils.getName(filename);
        proto.setName(FilenameUtils.removeExtension(shortFilename));
        push(proto);
    }

    @SuppressWarnings("unchecked")
    public <T> T peek(Class<T> declarationClass) {
        Object declaration = declarationStack.peek();
        if (declarationClass.isAssignableFrom(declaration.getClass())) {
            return (T) declaration;
        }
        return fail(declaration, declarationClass);
    }

    public void push(Object declaration) {
        declarationStack.push(declaration);
    }

    @SuppressWarnings("unchecked")
    public <T> T pop(Class<T> declarationClass) {
        Object declaration = declarationStack.pop();
        if (declarationClass.isAssignableFrom(declaration.getClass())) {
            return (T) declaration;
        }
        return fail(declaration, declarationClass);
    }

    /**
     * Register user type in symbol table. Full name should start with ".".
     */
    public void register(String fullName, Type type) {
        if (symbolTable.containsKey(fullName)) {
            LOGGER.error("{} already registered", fullName);
        }
        symbolTable.put(fullName, type);
    }

    private <T> T fail(Object descriptor, Class<T> targetClass) {
        String source = descriptor.getClass().getSimpleName();
        String target = targetClass.getSimpleName();
        String message = String.format("Can not cast %s to %s", source, target);
        throw new IllegalStateException(message);
    }

    public Proto getProto() {
        return proto;
    }

    public <T extends Type> T resolve(String typeName, Class<T> clazz) {
        Type instance = resolve(typeName);
        if (instance == null) {
            return null;
        }
        if (clazz.isAssignableFrom(instance.getClass())) {
            return clazz.cast(instance);
        } else {
            throw new ParserException("Type error: %s of type %s can not be cast to %s",
                    typeName, instance.getClass(), clazz);
        }
    }

    public Type resolve(String typeName) {
        Type local = symbolTable.get(typeName);
        if (local != null) {
            return local;
        }
        for (ProtoContext importedContext : publicImports) {
            Type imported = importedContext.resolve(typeName);
            if (imported != null) {
                return imported;
            }
        }
        for (ProtoContext importedContext : imports) {
            Type imported = importedContext.resolveImport(typeName);
            if (imported != null) {
                return imported;
            }
        }
        return null;
    }

    public Type resolveImport(String typeName) {
        Type local = symbolTable.get(typeName);
        if (local != null) {
            return local;
        }
        for (ProtoContext importedContext : publicImports) {
            Type imported = importedContext.resolveImport(typeName);
            if (imported != null) {
                return imported;
            }
        }
        return null;
    }

    @Override
    public void registerExtension(Extension extension) {
        localExtensionRegistry.registerExtension(extension);
    }

    @Override
    public Collection<Extension> getExtensions(String messageName) {
        return localExtensionRegistry.getExtensions(messageName);
    }

    @Override
    public Map<String, Extension> getExtensionMap(String messageName) {
        return localExtensionRegistry.getExtensionMap(messageName);
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public List<ProtoContext> getImports() {
        return imports;
    }

    public void addImport(ProtoContext importedProto) {
        imports.add(importedProto);
    }

    public List<ProtoContext> getPublicImports() {
        return publicImports;
    }

    public void addPublicImport(ProtoContext importedProto) {
        publicImports.add(importedProto);
    }

}
