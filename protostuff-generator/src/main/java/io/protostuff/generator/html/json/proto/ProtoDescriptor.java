package io.protostuff.generator.html.json.proto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.protostuff.generator.html.json.index.NodeType;
import java.util.Map;
import javax.annotation.Nullable;
import org.immutables.value.Value;

/**
 * JSON node representing proto file.
 *
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableProtoDescriptor.class)
@JsonDeserialize(as = ImmutableProtoDescriptor.class)
public interface ProtoDescriptor {

    String name();

    NodeType type();

    String canonicalName();

    String filename();

    @Nullable
    String description();

    Map<String, Object> options();

}
