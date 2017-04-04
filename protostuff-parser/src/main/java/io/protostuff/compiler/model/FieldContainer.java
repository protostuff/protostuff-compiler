package io.protostuff.compiler.model;

import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FieldContainer extends Element {

    List<Field> getFields();

    void setFields(List<Field> fields);

    int getFieldCount();

    Field getField(String name);

    Field getField(int tag);

    void addField(Field field);
}
