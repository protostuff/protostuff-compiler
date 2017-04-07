package io.protostuff.generator.html.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.Type;

/**
 * Usage type - defines where user type is used - as an RPC method request/response
 * type or as a field type in other message.
 */
public enum UsageType {

    @JsonProperty("message")
    MESSAGE,

    @JsonProperty("service")
    SERVICE;

    /**
     * Get usage type based on corresponding proto type.
     */
    public static UsageType from(Type type) {
        if (type instanceof Message) {
            return MESSAGE;
        }
        if (type instanceof Service) {
            return SERVICE;
        }
        throw new IllegalArgumentException(type.toString());
    }
}
