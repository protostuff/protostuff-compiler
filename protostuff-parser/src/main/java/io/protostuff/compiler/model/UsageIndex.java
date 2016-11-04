package io.protostuff.compiler.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import io.protostuff.compiler.parser.ProtoWalker;

import java.util.Collection;

public class UsageIndex {

    private Multimap<Type, Type> index = HashMultimap.create();

    public static UsageIndex build(Collection<Proto> protos) {
        UsageIndex usageIndex = new UsageIndex();
        protos.forEach(proto -> {
            ProtoWalker.newInstance(proto.getContext())
                    .onMessage((context, message) -> {
                        for (Field field : message.getFields()) {
                            usageIndex.register(field.getType(), message);
                        }
                    })
                    .onService((context, service) -> {
                        for (io.protostuff.compiler.model.ServiceMethod serviceMethod : service.getMethods()) {
                            usageIndex.register(serviceMethod.getArgType(), service);
                            usageIndex.register(serviceMethod.getReturnType(), service);
                        }
                    })
                    .walk();
        });
        return usageIndex;
    }

    private void register(Type type, Type usedInType) {
        index.put(type, usedInType);
    }

    public Collection<Type> getUsages(Type type) {
        return index.get(type);
    }

}
