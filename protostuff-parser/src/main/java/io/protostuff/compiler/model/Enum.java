package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enum node of proto file.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class Enum extends AbstractDescriptor implements UserType {

    protected final UserTypeContainer parent;
    protected List<EnumConstant> constants = new ArrayList<>();
    protected Proto proto;
    protected String fullyQualifiedName;
    protected List<Range> reservedFieldRanges = new ArrayList<>();
    protected List<String> reservedFieldNames = new ArrayList<>();

    public Enum(UserTypeContainer parent) {
        this.parent = parent;
    }

    public List<EnumConstant> getConstants() {
        return constants;
    }

    public void setConstants(List<EnumConstant> constants) {
        this.constants = constants;
    }

    /**
     * Returns a list of all enum constant names.
     */
    public Set<String> getConstantNames() {
        return constants.stream()
                .map(EnumConstant::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Get enum constant by it's name.
     */
    public EnumConstant getConstant(String name) {
        for (EnumConstant enumConstant : getConstants()) {
            if (enumConstant.getName().equals(name)) {
                return enumConstant;
            }
        }
        return null;
    }

    public void addConstant(EnumConstant value) {
        constants.add(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("name", name)
                .add("fullyQualifiedName", fullyQualifiedName)
                .add("constants", constants)
                .add("options", options)
                .toString();
    }

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.ENUM;
    }


    @Override
    public boolean isScalar() {
        return true;
    }

    @Override
    public boolean isEnum() {
        return true;
    }

    @Override
    public boolean isMessage() {
        return false;
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
    public boolean isMap() {
        return false;
    }

    @Override
    public UserTypeContainer getParent() {
        return parent;
    }

    @Override
    public boolean isNested() {
        return getParent().getDescriptorType() != DescriptorType.PROTO;
    }

    @Override
    public void addReservedFieldRange(Range range) {
        reservedFieldRanges.add(range);
    }

    @Override
    public void addReservedFieldName(String fieldName) {
        reservedFieldNames.add(fieldName);
    }

    @Override
    public List<Range> getReservedFieldRanges() {
        return reservedFieldRanges;
    }

    @Override
    public List<String> getReservedFieldNames() {
        return reservedFieldNames;
    }
}
