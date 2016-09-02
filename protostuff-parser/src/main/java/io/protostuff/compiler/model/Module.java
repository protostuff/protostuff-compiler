package io.protostuff.compiler.model;

import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Value.Immutable
public interface Module {

    String getName();

    List<Proto> getProtos();

    String getOutput();

    Map<String, Object> getOptions();

}
