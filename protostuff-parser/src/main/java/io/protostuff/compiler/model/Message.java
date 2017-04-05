package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.protostuff.compiler.parser.MessageParseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Message node.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class Message extends AbstractUserTypeContainer
        implements UserType, UserTypeContainer, FieldContainer, GroupContainer {

    protected List<Field> fields = new ArrayList<>();
    protected List<Group> groups = new ArrayList<>();
    protected List<Oneof> oneofs = new ArrayList<>();
    protected Proto proto;
    protected String fullyQualifiedName;

    protected List<Range> extensionRanges = new ArrayList<>();
    protected List<Range> reservedFieldRanges = new ArrayList<>();
    protected List<String> reservedFieldNames = new ArrayList<>();

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
    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public void addField(Field field) {
        fields.add(field);
    }

    @Override
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
        return oneofs;
    }

    public void setOneofs(List<Oneof> oneofs) {
        this.oneofs = oneofs;
    }

    public void addOneof(Oneof oneof) {
        oneofs.add(oneof);
    }

    /**
     * Get oneof node by it's name.
     */
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
    public String getCanonicalName() {
        String fqn = getFullyQualifiedName();
        if (fqn.startsWith(".")) {
            return fqn.substring(1);
        }
        return fqn;
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
        return groups;
    }

    @Override
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public void addGroup(Group group) {
        groups.add(group);
    }

    public List<Range> getExtensionRanges() {
        return extensionRanges;
    }

    public void setExtensionRanges(List<Range> extensionRanges) {
        this.extensionRanges = extensionRanges;
    }

    public void addExtensionRange(Range range) {
        extensionRanges.add(range);
    }

    public List<Range> getReservedFieldRanges() {
        return reservedFieldRanges;
    }

    public void setReservedFieldRanges(List<Range> reservedFieldRanges) {
        this.reservedFieldRanges = reservedFieldRanges;
    }

    public void addReservedFieldRange(Range range) {
        reservedFieldRanges.add(range);
    }

    public List<String> getReservedFieldNames() {
        return reservedFieldNames;
    }

    public void setReservedFieldNames(List<String> reservedFieldNames) {
        this.reservedFieldNames = reservedFieldNames;
    }

    public void addReservedFieldName(String name) {
        reservedFieldNames.add(name);
    }

    @Override
    public int getFieldCount() {
        return fields.size();
    }

    public boolean isMapEntry() {
        DynamicMessage.Value value = this.getOptions().get(MessageParseListener.OPTION_MAP_ENTRY);
        return value != null && value.isBooleanType() && value.getBoolean();
    }
}
