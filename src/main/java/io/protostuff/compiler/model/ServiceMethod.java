package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceMethod extends AbstractDescriptor {

    private String argTypeName;
    private Message argType;

    private String returnTypeName;
    private Message returnType;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("argTypeName", argTypeName)
                .add("returnTypeName", returnTypeName)
                .add("options", options)
                .toString();
    }
}
