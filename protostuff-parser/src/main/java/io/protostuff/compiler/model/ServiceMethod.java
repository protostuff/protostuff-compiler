package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceMethod extends AbstractDescriptor {

    private final Service parent;
    private String argTypeName;
    private Message argType;
    private boolean argStream;
    private String returnTypeName;
    private Message returnType;
    private boolean returnStream;

    public ServiceMethod(Service parent) {
        this.parent = parent;
    }

    @Override
    public Service getParent() {
        return parent;
    }

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.SERVICE_METHOD;
    }

    public String getArgTypeName() {
        return argTypeName;
    }

    public void setArgTypeName(String argTypeName) {
        this.argTypeName = argTypeName;
    }

    public Message getArgType() {
        return argType;
    }

    public void setArgType(Message argType) {
        this.argType = argType;
    }

    public String getReturnTypeName() {
        return returnTypeName;
    }

    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }

    public Message getReturnType() {
        return returnType;
    }

    public void setReturnType(Message returnType) {
        this.returnType = returnType;
    }

    public boolean isArgStream() {
        return argStream;
    }

    public void setArgStream(boolean argStream) {
        this.argStream = argStream;
    }

    public boolean isReturnStream() {
        return returnStream;
    }

    public void setReturnStream(boolean returnStream) {
        this.returnStream = returnStream;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("argTypeName", argTypeName)
                .add("argStream", argStream)
                .add("returnTypeName", returnTypeName)
                .add("returnStream", returnStream)
                .add("options", options)
                .toString();
    }
}
