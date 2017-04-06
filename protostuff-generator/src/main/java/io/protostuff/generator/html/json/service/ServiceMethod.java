package io.protostuff.generator.html.json.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import javax.annotation.Nullable;
import org.immutables.value.Value;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableServiceMethod.class)
@JsonDeserialize(as = ImmutableServiceMethod.class)
public interface ServiceMethod {

    String name();

    @Nullable
    String description();

    String argTypeId();

    String returnTypeId();

    Map<String, Object> options();
}
