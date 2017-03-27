package io.protostuff.generator.html.json.index;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableJsonTreeNode.class)
@JsonDeserialize(as = ImmutableJsonTreeNode.class)
public interface JsonTreeNode {

    String label();

    NodeData data();

    List<JsonTreeNode> children();

}
