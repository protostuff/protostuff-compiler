package io.protostuff.generator.html.uml;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.parboiled.common.Base64;
import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.VerbatimNode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Source: https://bitbucket.org/peachjean/pegdown-uml/src/3e053df209e8?at=default
 * Author: Jared Bunting, jared.bunting@peachjean.com
 * Licensed under GNU General Public License (GPL)
 * http://www.gnu.org/licenses/gpl.txt
 */
public class PlantUmlVerbatimSerializer implements VerbatimSerializer {

    public static void addToMap(final Map<String, VerbatimSerializer> serializerMap) {
        PlantUmlVerbatimSerializer serializer = new PlantUmlVerbatimSerializer();
        for (Type type : Type.values()) {
            serializerMap.put(type.name(), serializer);
        }
    }

    @Override
    public void serialize(VerbatimNode node, Printer printer) {
        Type type = Type.valueOf(node.getType());

        String formatted = type.wrap(node.getText());
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SourceStringReader reader = new SourceStringReader(formatted);
        String desc;
        try {
            desc = reader.generateImage(baos, type.getFormatOption());
        } catch (IOException e) {
            throw new RuntimeException("Could not generate uml for node " + node, e);
        }
        final String rendered = type.render(baos.toByteArray(), desc);
        printer.print(rendered);
    }

    enum Type {
        uml(OutputType.svg),
        dot(OutputType.svg),
        jcckit(OutputType.png),
        ditaa(OutputType.png);

        private final OutputType outputType;

        Type(final OutputType outputType) {
            this.outputType = outputType;
        }

        public String wrap(final String originalText) {
            return "@start" + this.toString() + "\n" + originalText + "@end" + this.toString() + "\n";
        }

        public FileFormatOption getFormatOption() {
            return this.outputType.getFormatOption();
        }

        public String render(byte[] bytes, final String desc) {
            return this.outputType.render(bytes, desc);
        }
    }

    enum OutputType {
        svg {
            public FileFormatOption getFormatOption() {
                return new FileFormatOption(FileFormat.SVG);
            }

            public String render(byte[] bytes, final String desc) {
                return new String(bytes, StandardCharsets.UTF_8);
            }
        }, png {
            @Override
            public FileFormatOption getFormatOption() {
                return new FileFormatOption(FileFormat.PNG);
            }

            @Override
            public String render(final byte[] bytes, final String desc) {
                return String.format("<img alt=\"%s\" src=\"data:image/png;base64,%s\"/>", desc, Base64.rfc2045().encodeToString(bytes, false));
            }
        };

        public abstract FileFormatOption getFormatOption();

        public abstract String render(byte[] bytes, final String desc);
    }
}
