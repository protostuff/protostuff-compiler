package io.protostuff.compiler.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.protostuff.compiler.parser.ProtoWalker;
import java.util.Collection;
import java.util.Collections;

public class UsageIndex {

    private Multimap<Type, Type> index = HashMultimap.create();

    public static UsageIndex build(Proto proto) {
        return build(Collections.singletonList(proto));
    }

    /**
     * Build usage index for given collection of proto files.
     */
    public static UsageIndex build(Collection<Proto> protos) {
        UsageIndex usageIndex = new UsageIndex();
        for (Proto proto : protos) {
            ProtoWalker.newInstance(proto.getContext())
                    .onMessage(message -> {
                        for (Field field : message.getFields()) {
                            usageIndex.register(field.getType(), message);
                        }
                    })
                    .onService(service -> {
                        for (ServiceMethod serviceMethod : service.getMethods()) {
                            usageIndex.register(serviceMethod.getArgType(), service);
                            usageIndex.register(serviceMethod.getReturnType(), service);
                        }
                    })
                    .walk();
        }
        return usageIndex;
    }

    private void register(Type type, Type usedInType) {
        index.put(type, usedInType);
    }

    public Collection<Type> getUsages(Type type) {
        return index.get(type);
    }

}
