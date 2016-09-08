package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.protostuff.compiler.parser.MessageParseListener;

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
    protected Proto proto;
    protected String fullyQualifiedName;

    protected List<Range> extensionRanges;
    protected List<Range> reservedFieldRanges;
    protected List<String> reservedFieldNames;

    public Message(UserTypeContainer parent) {
        super(parent);
    }

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.MESSAGE;
    }

    /**
     * Returns all fields in this message, including group fields and oneof fields.
     */
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
            fields = new ArrayList<Field>();
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
            oneofs = new ArrayList<Oneof>();
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
    public String getCanonicalName() {
        String fqn = getFullyQualifiedName();
        if (fqn.startsWith(".")) {
            return fqn.substring(1);
        }
        return fqn;
    }

    @Override
    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    @Override
    public boolean isNested() {
        return getParent().getDescriptorType() != DescriptorType.PROTO;
    }

    @Override
    public boolean isScalar() {
        return false;
    }

    @Override
    public boolean isEnum() {
        return false;
    }

    @Override
    public boolean isMessage() {
        return true;
    }

    @Override
    public boolean isMap() {
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
            groups = new ArrayList<Group>();
        }
        groups.add(group);
    }

    public List<Range> getExtensionRanges() {
        if (extensionRanges == null) {
            return Collections.emptyList();
        }
        return extensionRanges;
    }

    public void setExtensionRanges(List<Range> extensionRanges) {
        this.extensionRanges = extensionRanges;
    }

    public void addExtensionRange(Range range) {
        if (extensionRanges == null) {
            extensionRanges = new ArrayList<Range>();
        }
        extensionRanges.add(range);
    }

    public List<Range> getReservedFieldRanges() {
        if (reservedFieldRanges == null) {
            return Collections.emptyList();
        }
        return reservedFieldRanges;
    }

    public void setReservedFieldRanges(List<Range> reservedFieldRanges) {
        this.reservedFieldRanges = reservedFieldRanges;
    }

    public void addReservedFieldRange(Range range) {
        if (reservedFieldRanges == null) {
            reservedFieldRanges = new ArrayList<Range>();
        }
        reservedFieldRanges.add(range);
    }

    public List<String> getReservedFieldNames() {
        if (reservedFieldNames == null) {
            return Collections.emptyList();
        }
        return reservedFieldNames;
    }

    public void setReservedFieldNames(List<String> reservedFieldNames) {
        this.reservedFieldNames = reservedFieldNames;
    }

    public void addReservedFieldName(String name) {
        if (reservedFieldNames == null) {
            reservedFieldNames = new ArrayList<String>();
        }
        reservedFieldNames.add(name);
    }

    @Override
    public int getFieldCount() {
        if (fields == null) {
            return 0;
        }
        return fields.size();
    }

    public boolean isMapEntry() {
        DynamicMessage.Value value = this.getOptions().get(MessageParseListener.OPTION_MAP_ENTRY);
        return value != null && value.isBooleanType() && value.getBoolean();
    }

    @Override
    public Enum getEnum(String name) {
        for (Enum anEnum : getEnums()) {
            if (name.equals(anEnum.getName())) {
                return anEnum;
            }
        }
        return null;
    }
}
