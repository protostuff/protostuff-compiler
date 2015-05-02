package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.AbstractDescriptor;
import io.protostuff.compiler.model.FieldType;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoContext.class);

    private final Map<String, UserType> symbolTable;
    private final Deque<AbstractDescriptor> declarationStack;

    private final Proto proto;

    public ProtoContext(String name) {
        symbolTable = new HashMap<>();
        declarationStack = new ArrayDeque<>();
        proto = new Proto();
        proto.setName(name);
        push(proto);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractDescriptor> T peek(Class<T> declarationClass) {
        AbstractDescriptor declaration = declarationStack.peek();
        if (declarationClass.isAssignableFrom(declaration.getClass())) {
            return (T) declaration;
        }
        return fail(declaration, declarationClass);
    }

    public void push(AbstractDescriptor declaration) {
        declarationStack.push(declaration);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractDescriptor> T pop(Class<T> declarationClass) {
        AbstractDescriptor declaration = declarationStack.pop();
        if (declarationClass.isAssignableFrom(declaration.getClass())) {
            return (T) declaration;
        }
        return fail(declaration, declarationClass);
    }

    /**
     * Register user type in symbol table. Full name should start with ".".
     */
    public void register(String fullName, UserType type) {
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

    public UserType resolve(String typeName) {
        return symbolTable.get(typeName);
    }
}
