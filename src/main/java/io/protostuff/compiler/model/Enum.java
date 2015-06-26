package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Enum extends AbstractUserFieldType {

    protected List<EnumConstant> values;

    public List<EnumConstant> getValues() {
        if (values == null) {
            return Collections.emptyList();
        }
        return values;
    }

    public void setValues(List<EnumConstant> values) {
        this.values = values;
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
