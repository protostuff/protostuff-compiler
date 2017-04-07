package io.protostuff.generator.html.json.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import javax.annotation.Nullable;
import org.immutables.value.Value;

/**
 * JSON node representing enum constant.
 *
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableEnumConstant.class)
@JsonDeserialize(as = ImmutableEnumConstant.class)
public interface EnumConstant {

    String name();

    @Nullable
    String description();

    int value();

    Map<String, Object> options();
}
