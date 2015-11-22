package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Message extends AbstractUserTypeContainer
        implements UserType, UserTypeContainer, FieldContainer, GroupContainer {

    protected List<Field> fields;
    protected List<Group> groups;
    protected List<Oneof> oneofs;
    protected List<Map> maps;
    protected Proto proto;
    protected String fullyQualifiedName;

    protected List<ExtensionRange> extensionRanges;

    public Message(UserTypeContainer parent) {
        super(parent);
    }

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

    public List<Map> getMaps() {
        if (maps == null) {
            return Collections.emptyList();
        }
        return maps;
    }

    public void setMaps(List<Map> maps) {
        this.maps = maps;
    }

    public Map getMap(String name) {
        for (Map map : getMaps()) {
            if (map.getName().equals(name)) {
                return map;
            }
        }
        return null;
    }

    public void addMap(Map map) {
        if (maps == null) {
            maps = new ArrayList<>();
        }
        maps.add(map);
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
                .add("fullyQualifiedName", getFullyQualifiedName())
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
    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    @Override
    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    @Override
    public boolean isScalar() {
        return false;
    }

    @Override
    public String getNamespace() {
        Preconditions.checkNotNull(fullyQualifiedName, "message is not initialized");
        return fullyQualifiedName + ".";
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
