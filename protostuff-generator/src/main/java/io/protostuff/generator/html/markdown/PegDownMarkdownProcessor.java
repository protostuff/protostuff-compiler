package io.protostuff.generator.html.markdown;

import static org.pegdown.Extensions.ABBREVIATIONS;
import static org.pegdown.Extensions.ATXHEADERSPACE;
import static org.pegdown.Extensions.AUTOLINKS;
import static org.pegdown.Extensions.DEFINITIONS;
import static org.pegdown.Extensions.FENCED_CODE_BLOCKS;
import static org.pegdown.Extensions.FORCELISTITEMPARA;
import static org.pegdown.Extensions.RELAXEDHRULES;
import static org.pegdown.Extensions.SMARTYPANTS;
import static org.pegdown.Extensions.STRIKETHROUGH;
import static org.pegdown.Extensions.SUPPRESS_ALL_HTML;
import static org.pegdown.Extensions.TABLES;
import static org.pegdown.Extensions.TASKLISTITEMS;
import static org.pegdown.Extensions.WIKILINKS;

import io.protostuff.generator.html.uml.PlantUmlVerbatimSerializer;
import java.util.HashMap;
import java.util.Map;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.VerbatimSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
