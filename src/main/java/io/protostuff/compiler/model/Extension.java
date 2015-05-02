package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Extension extends Field {

    protected String extendeeName;
    protected Message extendee;

    public String getExtendeeName() {
        return extendeeName;
    }

    public void setExtendeeName(String extendeeName) {
        this.extendeeName = extendeeName;
    }

    public Message getExtendee() {
        return extendee;
    }

    public void setExtendee(Message extendee) {
        this.extendee = extendee;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("name", name)
                .add("extendeeName", extendeeName)
                .add("modifier", modifier)
                .add("typeName", typeName)
                .add("tag", tag)
                .add("options", options)
                .toString();
    }
}
