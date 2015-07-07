package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Service extends AbstractDescriptor implements Type {

    private Proto proto;
    private String fullName;
    private List<ServiceMethod> methods;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getReference() {
        return fullName;
    }

    public List<ServiceMethod> getMethods() {
        if (methods == null) {
            return Collections.emptyList();
        }
        return methods;
    }

    public void setMethods(List<ServiceMethod> methods) {
        this.methods = methods;
    }

    public ServiceMethod getMethod(String name) {
        for (ServiceMethod serviceMethod : getMethods()) {
            if (serviceMethod.getName().equals(name)) {
                return serviceMethod;
            }
        }
        return null;
    }

    public void addMethod(ServiceMethod method) {
        if (methods == null) {
            methods = new ArrayList<>();
        }
        methods.add(method);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("fullName", fullName)
                .add("methods", methods)
                .add("options", options)
                .toString();
    }

}
