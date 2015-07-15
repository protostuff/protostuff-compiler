package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Extension extends AbstractElement implements FieldContainer, GroupContainer {

    protected String extendeeName;
    protected Message extendee;

    protected List<Field> fields;
    protected List<Group> groups;

    protected String namespace;

    private final UserTypeContainer parent;

    public Extension(UserTypeContainer parent) {
        this.parent = parent;
    }

    public UserTypeContainer getParent() {
        return parent;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

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

    public List<Field> getFields() {
        if (fields == null) {
            return Collections.emptyList();
        }
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(field);
    }

    @Nullable
    public Field getField(String name) {
        for (Field field : getFields()) {
            if (name.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    @Override
    public Field getField(int tag) {
        for (Field field : getFields()) {
            if (tag == field.getTag()) {
                return field;
            }
        }
        return null;
    }

    @Override
    public List<Group> getGroups() {
        if (groups == null) {
            return Collections.emptyList();
        }
        return groups;
    }

    @Override
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public void addGroup(Group group) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(group);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("extendeeName", extendeeName)
                .add("fields", fields)
                .toString();
    }
}
