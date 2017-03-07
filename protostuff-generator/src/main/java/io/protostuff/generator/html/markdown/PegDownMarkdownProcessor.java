package io.protostuff.generator.html.markdown;

import io.protostuff.generator.html.uml.PlantUmlVerbatimSerializer;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.VerbatimSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static org.pegdown.Extensions.*;

public class PegDownMarkdownProcessor implements MarkdownProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PegDownMarkdownProcessor.class);

    private static final int OPTIONS = SMARTYPANTS
            | ABBREVIATIONS
            | AUTOLINKS
            | TABLES
            | FENCED_CODE_BLOCKS
            | DEFINITIONS
            | SUPPRESS_ALL_HTML
            | STRIKETHROUGH
            | ATXHEADERSPACE
            | FORCELISTITEMPARA
            | RELAXEDHRULES
            | TASKLISTITEMS
            | WIKILINKS;

    private final PegDownProcessor processor = new PegDownProcessor(OPTIONS);

    @Inject
    public PegDownMarkdownProcessor() {
    }

    @Override
    public String toHtml(String source) {
        try {
            Map<String, VerbatimSerializer> serializers = new HashMap<>();
            PlantUmlVerbatimSerializer.addToMap(serializers);
            LinkRenderer linkRenderer = new TypeLinkRenderer();
            return processor.markdownToHtml(source, linkRenderer, serializers);
        } catch (Exception e) {
            LOGGER.error("Could not convert given source to markdown: {}", source, e);
            return source;
        }
    }

}
