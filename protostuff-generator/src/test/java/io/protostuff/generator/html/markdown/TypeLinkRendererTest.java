package io.protostuff.generator.html.markdown;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pegdown.LinkRenderer;
import org.pegdown.ast.WikiLinkNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class TypeLinkRendererTest {

    private TypeLinkRenderer renderer;

    @BeforeEach
    void setUp() {
        renderer = new TypeLinkRenderer();
    }

    @Test
    void renderWithDefaultText() {
        LinkRenderer.Rendering rendering = renderer.render(new WikiLinkNode("io.protostuff.Test"));
        assertEquals("#/types/io.protostuff.Test", rendering.href);
        assertEquals("io.protostuff.Test", rendering.text);
    }

    @Test
    void renderWithCustomText() {
        LinkRenderer.Rendering rendering = renderer.render(new WikiLinkNode("io.protostuff.Test|Test"));
        assertEquals("#/types/io.protostuff.Test", rendering.href);
        assertEquals("Test", rendering.text);
    }

}