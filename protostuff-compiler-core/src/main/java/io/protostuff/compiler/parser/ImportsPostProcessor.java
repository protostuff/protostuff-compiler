package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Import;

import javax.inject.Inject;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ImportsPostProcessor implements ProtoContextPostProcessor {

    private final Importer importer;

    @Inject
    public ImportsPostProcessor(Importer importer) {
        this.importer = importer;
    }

    @Override
    public void process(ProtoContext context) {
        resolveImports(context);
    }

    private void resolveImports(ProtoContext context) {
        for (Import anImport : context.getProto().getImports()) {
            ProtoContext importedContext = importer.importFile(anImport.getValue());
            if (anImport.isPublic()) {
                context.addPublicImport(importedContext);
            } else {
                context.addImport(importedContext);
            }
            anImport.setProto(importedContext.getProto());
        }
    }
}
