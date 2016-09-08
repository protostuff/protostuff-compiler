package io.protostuff.generator.html.json.service;

import javax.annotation.Nullable;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceMethod {

    private String name;
    @Nullable
    private String description;
    private String argTypeId;
    private String returnTypeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public String getArgTypeId() {
        return argTypeId;
    }

    public void setArgTypeId(String argTypeId) {
        this.argTypeId = argTypeId;
    }

    public String getReturnTypeId() {
        return returnTypeId;
    }

    public void setReturnTypeId(String returnTypeId) {
        this.returnTypeId = returnTypeId;
    }
}
