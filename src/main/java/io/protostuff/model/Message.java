package io.protostuff.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Message extends AbstractUserTypeContainer implements UserType, UserTypeContainer {

    protected List<MessageField> fields;
    protected Proto proto;
    protected String fullName;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("name", name)
                .add("fullName", getFullName())
                .add("fields", fields)
                .add("messages", messages)
                .add("enums", enums)
                .add("options", options)
                .toString();
    }

    @Override
    public Proto getProto() {
        return proto;
    }

    @Override
    public void setProto(Proto proto) {
        this.proto = proto;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getNamespacePrefix() {
        Preconditions.checkNotNull(fullName, "message is not initialized");
        return fullName + ".";
    }
}
