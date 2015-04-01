package io.protostuff.parser;

import io.protostuff.proto3.AbstractDescriptor;
import io.protostuff.proto3.FileDescriptor;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Context {

    private final Map<String, AbstractDescriptor> symbolTable;
    private final Deque<AbstractDescriptor> declarationStack;
    private FileDescriptor result;

    public Context() {
        symbolTable = new HashMap<>();
        declarationStack = new ArrayDeque<>();
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


    private <T> T fail(Object descriptor, Class<T> targetClass) {
        String source = descriptor.getClass().getSimpleName();
        String target = targetClass.getSimpleName();
        String message = String.format("Can not cast %s to %s", source, target);
        throw new IllegalStateException(message);
    }

    @Nullable
    public FileDescriptor getResult() {
        return result;
    }

    public void setResult(FileDescriptor result) {
        checkState(this.result == null);
        this.result = result;
    }
}
