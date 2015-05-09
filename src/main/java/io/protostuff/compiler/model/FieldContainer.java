package io.protostuff.compiler.model;

import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface FieldContainer {

    List<Field> getFields();

    Field getField(String name);

    Field getField(int tag);

    void setFields(List<Field> fields);

    void addField(Field field);
}
