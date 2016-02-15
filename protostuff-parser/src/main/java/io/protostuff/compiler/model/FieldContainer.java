package io.protostuff.compiler.model;

import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FieldContainer extends Element {

    List<Field> getFields();

    int getFieldCount();

    void setFields(List<Field> fields);

    Field getField(String name);

    Field getField(int tag);

    void addField(Field field);
}
