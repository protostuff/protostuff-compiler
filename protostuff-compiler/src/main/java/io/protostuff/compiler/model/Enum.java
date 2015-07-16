package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Enum extends AbstractUserType {

    protected List<EnumConstant> values;

    public Enum(UserTypeContainer parent) {
        super(parent);
    }

    public List<EnumConstant> getValues() {
        if (values == null) {
            return Collections.emptyList();
        }
        return values;
    }

    public Set<String> getValueNames() {
        if (values == null) {
            return Collections.emptySet();
        }
        return values.stream()
                .map(EnumConstant::getName)
                .collect(Collectors.toSet());
    }

    public void setValues(List<EnumConstant> values) {
        this.values = values;
    }

    public EnumConstant getValue(String name) {
        for (EnumConstant enumConstant : getValues()) {
            if (enumConstant.getName().equals(name)) {
                return enumConstant;
            }
        }
        return null;
    }

    public void addValue(EnumConstant value) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("name", name)
                .add("fullName", fullName)
                .add("values", values)
                .add("options", options)
                .toString();
    }

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.ENUM;
    }


}
