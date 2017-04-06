package io.protostuff.generator.html.json.pages;

import static io.protostuff.generator.html.HtmlGenerator.PAGES;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyList;

import io.protostuff.compiler.model.Module;
import io.protostuff.generator.GeneratorException;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.StaticPage;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import io.protostuff.generator.html.markdown.MarkdownProcessor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.commons.io.FilenameUtils;

public class JsonPageGenerator extends AbstractJsonGenerator {

    private final MarkdownProcessor markdownProcessor;

    @Inject
    public JsonPageGenerator(OutputStreamFactory outputStreamFactory, MarkdownProcessor markdownProcessor) {
        super(outputStreamFactory);
        this.markdownProcessor = markdownProcessor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void compile(Module module) {
        Map<String, Object> options = module.getOptions();
        Object pages = options.getOrDefault(PAGES, emptyList());
        for (StaticPage page : (List<StaticPage>) pages) {
            Path path = page.getFile().toPath();
            String content = readFile(path);
            String html = markdownProcessor.toHtml(content);
            String filename = page.getFile().getName();
            String baseName = FilenameUtils.getBaseName(filename);
            Page p = ImmutablePage.builder()
                    .name(page.getName())
                    .content(html)
                    .build();
            write(module, "data/pages/" + baseName + ".json", p);
        }
    }

    private String readFile(Path path) {
        try {
            return new String(Files.readAllBytes(path), UTF_8);
        } catch (IOException e) {
            throw new GeneratorException("Could not read %s", e, path);
        }
    }
}
