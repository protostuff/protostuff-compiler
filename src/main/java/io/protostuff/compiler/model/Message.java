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
    protected List<Oneof> oneofs;
    protected Proto proto;
    protected String fullName;
    protected boolean nested;
    protected UserTypeContainer parent;
    protected List<ExtensionRange> extensionRanges;

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.MESSAGE;
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

    public List<Oneof> getOneofs() {
        if (oneofs == null) {
            return Collections.emptyList();
        }
        return oneofs;
    }

    public void setOneofs(List<Oneof> oneofs) {
        this.oneofs = oneofs;
    }

    public void addOneof(Oneof oneof) {
        if (oneofs == null) {
            oneofs = new ArrayList<>();
        }
        oneofs.add(oneof);
    }

    public Oneof getOneof(String name) {
        for (Oneof oneof : getOneofs()) {
            if (name.equals(oneof.getName())) {
                return oneof;
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

    public List<ExtensionRange> getExtensionRanges() {
        if (extensionRanges == null) {
            return Collections.emptyList();
        }
        return extensionRanges;
    }

    public void setExtensionRanges(List<ExtensionRange> extensionRanges) {
        this.extensionRanges = extensionRanges;
    }

    public void addExtensionRange(ExtensionRange range) {
        if (extensionRanges == null) {
            extensionRanges = new ArrayList<>();
        }
        extensionRanges.add(range);
    }
}
