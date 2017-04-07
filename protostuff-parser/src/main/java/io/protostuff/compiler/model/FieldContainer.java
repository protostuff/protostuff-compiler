package io.protostuff.compiler.model;

import java.util.List;

/**
 * Base interface for field containers - messages, groups
 * and oneof nodes.
 *
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
