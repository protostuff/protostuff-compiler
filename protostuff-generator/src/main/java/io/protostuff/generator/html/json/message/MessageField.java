package io.protostuff.generator.html.json.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import javax.annotation.Nullable;
import org.immutables.value.Value;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableMessageField.class)
@JsonDeserialize(as = ImmutableMessageField.class)
public interface MessageField {
    String name();

    String typeId();

    MessageFieldModifier modifier();

    int tag();

    @Nullable
    String description();

    @Value.Default
    default boolean map() {
        return false;
    }

    @Nullable
    String mapKeyTypeId();

    @Nullable
    String mapValueTypeId();

    @Nullable
    String oneof();

    Map<String, Object> options();
}
