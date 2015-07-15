package io.protostuff.compiler.model;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface OptionContainer {

    DynamicMessage getOptions();

//    void addRawOption(String name, String value);

}
