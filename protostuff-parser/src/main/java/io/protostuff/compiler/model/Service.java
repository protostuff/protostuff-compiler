package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service node.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class Service extends AbstractDescriptor implements Type {

    private final Proto parent;
    private Proto proto;
    private String fullyQualifiedName;
    private List<ServiceMethod> methods = new ArrayList<>();

    public Service(Proto parent) {
        this.parent = parent;
    }

    @Override
    public Proto getParent() {
        return parent;
    }

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.SERVICE;
    }

    public Proto getProto() {
        return proto;
    }

    public void setProto(Proto proto) {
        this.proto = proto;
    }

    @Override
    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public List<ServiceMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<ServiceMethod> methods) {
        this.methods = methods;
    }

    /**
     * Get a service method by it's name.
     */
    public ServiceMethod getMethod(String name) {
        for (ServiceMethod serviceMethod : getMethods()) {
            if (serviceMethod.getName().equals(name)) {
                return serviceMethod;
            }
        }
        return null;
    }

    public void addMethod(ServiceMethod method) {
        methods.add(method);
    }

    @Override
    public String getCanonicalName() {
        return fullyQualifiedName.substring(1);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("fullyQualifiedName", fullyQualifiedName)
                .add("methods", methods)
                .add("options", options)
                .toString();
    }

}
