package io.protostuff.generator.html.markdown;

import org.pegdown.LinkRenderer;
import org.pegdown.ast.WikiLinkNode;

class TypeLinkRenderer extends LinkRenderer {

    @Override
    public Rendering render(WikiLinkNode node) {
        String source = node.getText();
        String url = source;
        String text = source;
        int pos;
        if ((pos = source.indexOf("|")) >= 0) {
            url = source.substring(0, pos);
            text = source.substring(pos + 1);
        }
        return new Rendering("#/types/" + url, text);
    }
}
