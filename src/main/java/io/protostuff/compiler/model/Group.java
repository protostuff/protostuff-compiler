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
public class Group extends Field implements FieldContainer, UserFieldType, UserTypeContainer, GroupContainer {

    protected FieldModifier modifier;
    protected int tag;
    protected List<Field> fields;
    protected Proto proto;
    protected String fullName;
    protected UserTypeContainer parent;
    protected List<Group> groups;


    protected List<Message> messages;
    protected List<Enum> enums;
    protected List<Extension> declaredExtensions;

    @Override
    public List<Message> getMessages() {
        if (messages == null) {
            return Collections.emptyList();
        }
        return messages;
    }

    @Override
    public void addMessage(Message message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }

    @Override
    public List<Enum> getEnums() {
        if (enums == null) {
            return Collections.emptyList();
        }
        return enums;
    }

    public void setEnums(List<Enum> enums) {
        this.enums = enums;
    }

    @Override
    public void addEnum(Enum e) {
        if (enums == null) {
            enums = new ArrayList<>();
        }
        enums.add(e);
    }

    @Override
    public List<Extension> getDeclaredExtensions() {
        if (declaredExtensions == null) {
            return Collections.emptyList();
        }
        return declaredExtensions;
    }

    @Override
    public void addDeclaredExtension(Extension extension) {
        if (declaredExtensions == null) {
            declaredExtensions = new ArrayList<>();
        }
        declaredExtensions.add(extension);
    }

    public FieldModifier getModifier() {
        return modifier;
    }

    public void setModifier(FieldModifier modifier) {
        this.modifier = modifier;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
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
    public String getReference() {
        return fullName;
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
    public boolean isNested() {
        return true;
    }

    @Override
    public void setNested(boolean nested) {
        throw new UnsupportedOperationException("group can not be top-level type");
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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("modifier", modifier)
                .add("tag", tag)
                .add("fields", fields)
                .add("options", options)
                .toString();
    }

    @Override
    public String getNamespace() {
        Preconditions.checkNotNull(fullName, "message is not initialized");
        return fullName + ".";
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

}
