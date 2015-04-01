package io.protostuff.proto3;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for all protocol buffer descriptors:
 * <ol>
 *     <li>message</li>
 *     <li>enum</li>
 *     <li>service</li>
 *     <li>method</li>
 * </ol>
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractDescriptor {

    protected String name;
    protected Map<String, Object> standardOptions;
    protected Map<String, Object> customOptions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getStandardOptions() {
        if (standardOptions == null) {
            return Collections.emptyMap();
        }
        return standardOptions;
    }

    @Nullable
    public Object getStandardOption(String name) {
        if (standardOptions == null) {
            return null;
        }
        return standardOptions.get(name);
    }

    public void setStandardOptions(Map<String, Object> standardOptions) {
        this.standardOptions = standardOptions;
    }

    public Map<String, Object> getCustomOptions() {
        if (customOptions == null) {
            return Collections.emptyMap();
        }
        return customOptions;
    }

    @Nullable
    public Object getCustomOption(String name) {
        if (customOptions == null) {
            return null;
        }
        return customOptions.get(name);
    }

    public void setCustomOptions(Map<String, Object> customOptions) {
        this.customOptions = customOptions;
    }

    public void addStandardOption(String name, Object value) {
        if (standardOptions == null) {
            standardOptions = new HashMap<>();
        }
        standardOptions.put(name, value);
    }

    public void addCustomOption(String name, Object value) {
        if (customOptions == null) {
            customOptions = new HashMap<>();
        }
        customOptions.put(name, value);
    }

}
