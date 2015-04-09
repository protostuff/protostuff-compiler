package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageField extends AbstractDescriptor {

    protected String type;
    protected int tag;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("name", name)
                .add("type", type)
                .add("tag", tag)
                .add("options", options)
                .toString();
    }

}
