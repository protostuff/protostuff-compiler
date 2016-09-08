package io.protostuff.generator.html.json.enumeration;

import javax.annotation.Nullable;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class EnumConstant {

    private String name;
    @Nullable
    private String description;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
