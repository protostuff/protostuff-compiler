package io.protostuff.model;

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
public abstract class AbstractDescriptor implements OptionContainer {

    protected String name;
    protected Map<String, Object> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<String, Object> getOptions() {
        if (options == null) {
            return Collections.emptyMap();
        }
        return options;
    }

    @Override
    @Nullable
    public Object getOption(String name) {
        if (options == null) {
            return null;
        }
        return options.get(name);
    }

    @Override
    public void addOption(String name, Object value) {
        if (options == null) {
            options = new HashMap<>();
        }
        options.put(name, value);
    }

}
