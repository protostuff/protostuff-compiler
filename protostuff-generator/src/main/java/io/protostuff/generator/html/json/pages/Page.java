package io.protostuff.generator.html.json.pages;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutablePage.class)
@JsonDeserialize(as = ImmutablePage.class)
public interface Page {

    String name();

    String ref();
}
