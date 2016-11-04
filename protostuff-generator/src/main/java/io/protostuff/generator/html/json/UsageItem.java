package io.protostuff.generator.html.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableUsageItem.class)
@JsonDeserialize(as = ImmutableUsageItem.class)
public interface UsageItem {

    String ref();

    UsageType type();
}
