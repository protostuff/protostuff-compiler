package io.protostuff.compiler.parser;

import javax.inject.Inject;

import io.protostuff.compiler.model.Import;

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
            FileReader reader = context.getFileReader();
            String file = anImport.getValue();
            ProtoContext importedContext = importer.importFile(reader, file);
            if (anImport.isPublic()) {
                context.addPublicImport(importedContext);
            } else {
                context.addImport(importedContext);
            }
            anImport.setProto(importedContext.getProto());
        }
    }
}
