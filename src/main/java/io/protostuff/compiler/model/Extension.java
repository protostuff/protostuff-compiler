package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Extension {

    protected Message target;
    protected List<MessageField> fields;
    protected ExtensionContainer parent;

    public Message getTarget() {
        return target;
    }

    public void setTarget(Message target) {
        this.target = target;
    }

    public List<MessageField> getFields() {
        if (fields == null) {
            return Collections.emptyList();
        }
        return fields;
    }

    public void setFields(List<MessageField> fields) {
        this.fields = fields;
    }

    public void addField(MessageField field) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(field);
    }

    @Nullable
    public MessageField getField(String name) {
        for (MessageField field : getFields()) {
            if (name.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    public ExtensionContainer getParent() {
        return parent;
    }

    public void setParent(ExtensionContainer parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("target", target)
                .add("fields", fields)
                .toString();
    }
}
