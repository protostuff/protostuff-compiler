package io.protostuff.generator.html.json.proto;

import javax.inject.Inject;

import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.Proto;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.json.index.NodeType;
import io.protostuff.generator.html.markdown.MarkdownProcessor;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class JsonProtoGenerator extends AbstractJsonGenerator {

    private final MarkdownProcessor markdownProcessor;

    @Inject
    public JsonProtoGenerator(OutputStreamFactory outputStreamFactory, MarkdownProcessor markdownProcessor) {
        super(outputStreamFactory);
        this.markdownProcessor = markdownProcessor;
    }

    @Override
    public void compile(Module module) {
        module.getProtos()
                .forEach(proto -> process(module, proto));
    }

    private void process(Module module, Proto proto) {
        String name = proto.getCanonicalName();
        ImmutableProtoDescriptor descriptor = ImmutableProtoDescriptor.builder()
                .name(proto.getName())
                .type(NodeType.PROTO)
                .canonicalName(proto.getCanonicalName())
                .filename(proto.getFilename())
                .description(markdownProcessor.toHtml(proto.getComments()))
                .options(proto.getOptions().toMap())
                .build();
        write(module, "data/proto/" + name + ".json", descriptor);
    }
}
