package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Element;
import io.protostuff.compiler.model.UserType;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Type;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoContext.class);

    private final String filename;
    private final Map<String, Type> symbolTable;
    private final Deque<Object> declarationStack;
    private final Proto proto;
    private final ExtensionRegistry extensionRegistry;
    private final List<ProtoContext> imports;
    private final List<ProtoContext> publicImports;

    private boolean initialized;
    private FileReader fileReader;

    public ProtoContext(String filename) {
        this.filename = filename;
        symbolTable = new HashMap<>();
        declarationStack = new ArrayDeque<>();
        imports = new ArrayList<>();
        publicImports = new ArrayList<>();
        proto = new Proto();
        proto.setContext(this);
        proto.setFilename(filename);
        proto.setName(getFilenameWithoutExtension(filename));
        push(proto);
        extensionRegistry = new ProtoExtensionRegistry(this);
    }

    private String getFilenameWithoutExtension(String filename) {
        String shortFilename = FilenameUtils.getName(filename);
        return FilenameUtils.removeExtension(shortFilename);
    }

    @SuppressWarnings("unchecked")
    public <T> T peek(Class<T> declarationClass) {
        Object declaration = declarationStack.peek();
        if (declaration == null) {
            throw new IllegalStateException("Declaration stack is empty");
        }
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
    public <T extends Type & Element> void register(String fullyQualifiedName, T type) {
        if (resolve(fullyQualifiedName) != null) {
            throw new ParserException(type, "Cannot register duplicate type: %s", fullyQualifiedName);
        }
        symbolTable.put(fullyQualifiedName, type);
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

    @SuppressWarnings("unchecked")
    public <T extends Type> T resolve(Class<T> typeClass, String fullyQualifiedName) {
        Type result = resolve(fullyQualifiedName);
        if (result == null) {
            return null;
        }
        if (typeClass.isAssignableFrom(result.getClass())) {
            return (T) result;
        }
        throw new ClassCastException(result.getClass() + " cannot be cast to " + typeClass);
    }

    public Type resolve(String fullyQualifiedName) {
        Type local = symbolTable.get(fullyQualifiedName);
        if (local != null) {
            return local;
        }
        for (ProtoContext importedContext : publicImports) {
            Type imported = importedContext.resolve(fullyQualifiedName);
            if (imported != null) {
                return imported;
            }
        }
        for (ProtoContext importedContext : imports) {
            Type imported = importedContext.resolveImport(fullyQualifiedName);
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

    public ExtensionRegistry getExtensionRegistry() {
        return extensionRegistry;
    }

    public FileReader getFileReader() {
        return fileReader;
    }

    public void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public String toString() {
        return "ProtoContext{'" + filename + "'}";
    }
}
