package io.protostuff.compiler.parser;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Provides {@link ProtoContext} for bundled copy of {@code google/protobuf/descriptor.proto}.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class DefaultDescriptorProtoProvider implements Provider<ProtoContext> {

    public static final String DESCRIPTOR_PROTO = "descriptor.proto";

    private final Importer importer;

    @Inject
    public DefaultDescriptorProtoProvider(Importer importer) {
        this.importer = importer;
    }

    @Override
    public ProtoContext get() {
        return importer.importFile(new ClasspathFileReader(), "google/protobuf/__descriptor.proto");
    }
}
