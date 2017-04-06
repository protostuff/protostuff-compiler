package io.protostuff.generator.html.json.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.protostuff.generator.html.json.UsageItem;
import io.protostuff.generator.html.json.index.NodeType;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.immutables.value.Value;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
@JsonSerialize(as = ImmutableMessageDescriptor.class)
@JsonDeserialize(as = ImmutableMessageDescriptor.class)
public interface MessageDescriptor {

    String name();

    NodeType type();

    String canonicalName();

    @Nullable
    String description();

    List<MessageField> fields();

    Map<String, Object> options();

    List<UsageItem> usages();
}
