package io.protostuff.compiler.model;

/**
 * Base class for all protocol buffer descriptors:
 * <ol>
 * <li>message</li>
 * <li>enum</li>
 * <li>service</li>
 * <li>method</li>
 * </ol>
 *
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractDescriptor extends AbstractElement implements Descriptor {

    protected String name;
    protected DynamicMessage options;

    public AbstractDescriptor() {
        this.options = new DynamicMessage();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DynamicMessage getOptions() {
        return options;
    }

}
