package io.protostuff.generator.html.uml;

import io.protostuff.generator.GeneratorException;
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
            String name = type.getName();
            serializerMap.put(name, serializer);
        }
    }

    @Override
    public void serialize(VerbatimNode node, Printer printer) {
        Type type = Type.getByName(node.getType());

        String formatted = type.wrap(node.getText());
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SourceStringReader reader = new SourceStringReader(formatted);
        String desc;
        try {
            desc = reader.generateImage(baos, type.getFormatOption());
        } catch (IOException e) {
            throw new GeneratorException("Could not generate uml for node " + node, e);
        }
        final String rendered = type.render(baos.toByteArray(), desc);
        printer.print(rendered);
    }

    enum Type {
        UML(OutputType.SVG),
        DOT(OutputType.SVG),
        JCCKIT(OutputType.PNG),
        DITAA(OutputType.PNG);

        private final OutputType outputType;

        Type(final OutputType outputType) {
            this.outputType = outputType;
        }

        public static Type getByName(String name) {
            return valueOf(name.toUpperCase());
        }

        public String wrap(final String originalText) {
            return "@start" + getName() + "\n" + originalText + "@end" + getName() + "\n";
        }

        public String getName() {
            return name().toLowerCase();
        }

        public FileFormatOption getFormatOption() {
            return this.outputType.getFormatOption();
        }

        public String render(byte[] bytes, final String desc) {
            return this.outputType.render(bytes, desc);
        }
    }

    enum OutputType {

        SVG {
            @Override
            public FileFormatOption getFormatOption() {
                return new FileFormatOption(FileFormat.SVG);
            }

            @Override
            public String render(byte[] bytes, final String desc) {
                return new String(bytes, StandardCharsets.UTF_8);
            }
        },

        PNG {
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
