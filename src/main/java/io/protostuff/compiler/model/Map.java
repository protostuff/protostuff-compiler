package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Map extends AbstractUserFieldType {

    private String keyTypeName;
    private String valueTypeName;
    private ScalarFieldType keyType;
    private Type valueType;

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.MAP;
    }

    public String getKeyTypeName() {
        return keyTypeName;
    }

    public void setKeyTypeName(String keyTypeName) {
        this.keyTypeName = keyTypeName;
    }

    public String getValueTypeName() {
        return valueTypeName;
    }

    public void setValueTypeName(String valueTypeName) {
        this.valueTypeName = valueTypeName;
    }

    public ScalarFieldType getKeyType() {
        return keyType;
    }

    public void setKeyType(ScalarFieldType keyType) {
        this.keyType = keyType;
    }

    public Type getValueType() {
        return valueType;
    }

    public void setValueType(Type valueType) {
        this.valueType = valueType;
    }
}
