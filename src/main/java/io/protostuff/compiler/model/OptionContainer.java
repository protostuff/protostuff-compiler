package io.protostuff.compiler.model;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface OptionContainer {

    Map<String, Object> getOptions();

    @Nullable
    Object getOption(String name);

    void addOption(String name, Object value);

}
