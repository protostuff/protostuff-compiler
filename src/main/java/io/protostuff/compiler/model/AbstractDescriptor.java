package io.protostuff.compiler.model;

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
    protected DynamicMessage options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractDescriptor() {
        this.options = new DynamicMessage();
    }

    @Override
    public DynamicMessage getOptions() {
        return options;
    }

}
