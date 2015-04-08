package io.protostuff.parser;

import io.protostuff.model.AbstractDescriptor;
import io.protostuff.model.Proto;
import io.protostuff.model.UserType;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoContext {

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

    public void register(String fullName, UserType type) {
        if (symbolTable.containsKey(fullName)) {
            throw new UnsupportedOperationException("TODO: implement");
        }
        symbolTable.put(fullName, type);
    }

    private <T> T fail(Object descriptor, Class<T> targetClass) {
        String source = descriptor.getClass().getSimpleName();
        String target = targetClass.getSimpleName();
        String message = String.format("Can not cast %s to %s", source, target);
        throw new IllegalStateException(message);
    }

    @Nullable
    public Proto getProto() {
        return proto;
    }

}
