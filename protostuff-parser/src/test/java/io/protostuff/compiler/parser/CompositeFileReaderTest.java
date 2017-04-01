package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.misc.Interval;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class CompositeFileReaderTest {

    @Test
    public void testRead() throws Exception {
        TestReader r1 = new TestReader("1.proto", "1");
        TestReader r2 = new TestReader("2.proto", "2");
        CompositeFileReader reader = new CompositeFileReader(r1, r2);
        CharStream s1 = reader.read("1.proto");
        CharStream s2 = reader.read("2.proto");
        CharStream s3 = reader.read("3.proto");
        assertNotNull(s1);
        assertNotNull(s2);
        assertNull(s3);
        assertEquals("1", s1.getText(Interval.of(0, 1)));
        assertEquals("2", s2.getText(Interval.of(0, 1)));
    }

    private static class TestReader implements FileReader {

        private final String name;
        private final String text;

        public TestReader(String name, String text) {
            this.name = name;
            this.text = text;
        }

        @Nullable
        @Override
        public CharStream read(String name) {
            if (this.name.equals(name)) {
                return CharStreams.fromString(text);
            }
            return null;
        }
    }
}