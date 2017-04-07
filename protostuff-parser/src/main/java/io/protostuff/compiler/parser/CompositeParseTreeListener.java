package io.protostuff.compiler.parser;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.Reflection;
import java.lang.reflect.Method;
import javax.annotation.ParametersAreNonnullByDefault;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * Composite parse tree listener.
 * Created in order to split complex parse tree listeners into
 * smaller ones, responsible for partial processing of an AST tree.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class CompositeParseTreeListener {

    private CompositeParseTreeListener() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Create new composite listener for a collection of delegates.
     */
    @SafeVarargs
    public static <T extends ParseTreeListener> T create(Class<T> type, T... delegates) {
        ImmutableList<T> listeners = ImmutableList.copyOf(delegates);
        return Reflection.newProxy(type, new AbstractInvocationHandler() {

            @Override
            @ParametersAreNonnullByDefault
            protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
                for (T listener : listeners) {
                    method.invoke(listener, args);
                }
                return null;
            }

            @Override
            public String toString() {
                return MoreObjects.toStringHelper("CompositeParseTreeListener")
                        .add("listeners", listeners)
                        .toString();
            }
        });

    }
}
