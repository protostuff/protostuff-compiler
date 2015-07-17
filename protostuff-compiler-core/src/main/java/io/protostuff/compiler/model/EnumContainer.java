package io.protostuff.compiler.model;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface EnumContainer {

    List<Enum> getEnums();

    /**
     * Get enum that is declared under this container.
     *
     * @param name the enum short name
     * @return enum instance or null if message with given name is not declared under
     * this container
     */
    @Nullable
    default Enum getEnum(String name) {
        for (Enum anEnum : getEnums()) {
            if (name.equals(anEnum.getName())) {
                return anEnum;
            }
        }
        return null;
    }

    void addEnum(Enum e);
}
