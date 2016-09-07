package io.protostuff.generator.html.json.message;

import javax.annotation.Nullable;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageField {
    private String name;
    private String typeId;
    private MessageFieldModifier modifier;
    private int tag;
    @Nullable
    private String description;
    private boolean map;
    @Nullable
    private String mapKeyTypeId;
    @Nullable
    private String mapValueTypeId;
    @Nullable
    private String oneof;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public MessageFieldModifier getModifier() {
        return modifier;
    }

    public void setModifier(MessageFieldModifier modifier) {
        this.modifier = modifier;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public boolean isMap() {
        return map;
    }

    public void setMap(boolean map) {
        this.map = map;
    }

    @Nullable
    public String getMapKeyTypeId() {
        return mapKeyTypeId;
    }

    public void setMapKeyTypeId(@Nullable String mapKeyTypeId) {
        this.mapKeyTypeId = mapKeyTypeId;
    }

    @Nullable
    public String getMapValueTypeId() {
        return mapValueTypeId;
    }

    public void setMapValueTypeId(@Nullable String mapValueTypeId) {
        this.mapValueTypeId = mapValueTypeId;
    }

    @Nullable
    public String getOneof() {
        return oneof;
    }

    public void setOneof(@Nullable String oneof) {
        this.oneof = oneof;
    }
}
