package io.protostuff.proto3;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Message extends AbstractUserTypeContainer {

    protected List<MessageField> fields;

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
                .add("fields", fields)
                .add("messages", messages)
                .add("enums", enums)
                .add("standardOptions", standardOptions)
                .add("customOptions", customOptions)
                .toString();
    }

}
