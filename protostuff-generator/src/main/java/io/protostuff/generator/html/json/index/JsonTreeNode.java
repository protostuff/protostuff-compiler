package io.protostuff.generator.html.json.index;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import org.immutables.value.Value;

/**
 * JSON index node for left tree panel.
 *
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
