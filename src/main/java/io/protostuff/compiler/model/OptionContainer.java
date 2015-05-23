package io.protostuff.compiler.model;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface OptionContainer {

    DynamicMessage getOptions();

//    void addRawOption(String name, String value);

}
