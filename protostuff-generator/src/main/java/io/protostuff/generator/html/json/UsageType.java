package io.protostuff.generator.html.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.Type;

public enum UsageType {

    @JsonProperty("message")
    MESSAGE,

    @JsonProperty("service")
    SERVICE;

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
