package io.protostuff.generator.html.json.pages;

import static io.protostuff.generator.html.HtmlGenerator.PAGES;

import com.google.common.base.Throwables;
import io.protostuff.compiler.model.Module;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.StaticPage;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.uml.PlantUmlVerbatimSerializer;
import org.apache.commons.io.FilenameUtils;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.VerbatimSerializer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class JsonPageGenerator extends AbstractJsonGenerator {

    private final PegDownProcessor pegDownProcessor;

    @Inject
    public JsonPageGenerator(OutputStreamFactory outputStreamFactory, PegDownProcessor pegDownProcessor) {
        super(outputStreamFactory);
        this.pegDownProcessor = pegDownProcessor;
    }

    @Override
    public void compile(Module module) {
        try {
            @SuppressWarnings("unchecked")
            List<StaticPage> pages = (List<StaticPage>) module.getOptions().get(PAGES);
            if (pages != null) {
                pages.forEach(page -> {
                    try {
                        String content = new String(Files.readAllBytes(page.getFile().toPath()), StandardCharsets.UTF_8);
                        Map<String, VerbatimSerializer> serializers = new HashMap<>();
                        PlantUmlVerbatimSerializer.addToMap(serializers);
                        String html = pegDownProcessor.markdownToHtml(content, new LinkRenderer(), serializers);
                        String baseName = FilenameUtils.getBaseName(page.getFile().getName());
                        Page p = ImmutablePage.builder()
                                .name(page.getName())
                                .content(html)
                                .build();
                        write(module.getOutput() + "/data/pages/" + baseName + ".json", p);
                    } catch (Exception e) {
                        throw Throwables.propagate(e);
                    }
                });
            }
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
