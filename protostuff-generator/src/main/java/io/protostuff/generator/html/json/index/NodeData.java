package io.protostuff.generator.html.json.index;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableNodeData.class)
@JsonDeserialize(as = ImmutableNodeData.class)
public interface NodeData {

    NodeType type();

    String ref();

}
