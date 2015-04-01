package io.protostuff.proto3;

import com.google.common.base.MoreObjects;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Enum extends AbstractDescriptor {

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
                .add("values", values)
                .add("standardOptions", standardOptions)
                .add("customOptions", customOptions)
                .toString();
    }

}
