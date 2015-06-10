package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Message extends AbstractUserTypeContainer
        implements UserFieldType, UserTypeContainer, FieldContainer, GroupContainer {

    protected List<Field> fields;
    protected List<Group> groups;
    protected Proto proto;
    protected String fullName;
    protected boolean nested;
    protected UserTypeContainer parent;
    protected List<Extension> extensions;

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
    public String getNamespace() {
        Preconditions.checkNotNull(fullName, "message is not initialized");
        return fullName + ".";
    }

    @Override
    public boolean isNested() {
        return nested;
    }

    @Override
    public void setNested(boolean nested) {
        this.nested = nested;
    }

    @Override
    public UserTypeContainer getParent() {
        return parent;
    }

    @Override
    public void setParent(UserTypeContainer parent) {
        this.parent = parent;
    }

    @Override
    public String getReference() {
        return fullName;
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

    public List<Extension> getExtensions() {
        if (extensions == null) {
            return Collections.emptyList();
        }
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public void addExtension(Extension extension) {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        extensions.add(extension);
    }

    @Nullable
    public Field getExtensionField(String fullExtensionFieldName) {
        for (Extension extension : getExtensions()) {
            for (Field field : extension.getFields()) {
                String fullFieldName = getFullExtensionFieldName(extension, field);
                if (fullFieldName.equals(fullExtensionFieldName)) {
                    return field;
                }
            }
        }
        return null;
    }

    private String getFullExtensionFieldName(Extension extension, Field field) {
        String namespace = extension.getNamespace();
        return namespace + field.getName();
    }
}
