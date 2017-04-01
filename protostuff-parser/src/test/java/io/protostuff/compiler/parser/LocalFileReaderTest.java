package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.misc.Interval;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class LocalFileReaderTest {

    private Path tempDirectory1;
    private Path tempDirectory2;
    private Path file1;
    private Path file2;

    @BeforeEach
    public void setUp() throws Exception {
        tempDirectory1 = Files.createTempDirectory("protostuff-test-");
        file1 = Files.write(tempDirectory1.resolve("1.proto"), "1".getBytes(StandardCharsets.UTF_8));
        tempDirectory2 = Files.createTempDirectory("protostuff-test-");
        file2 = Files.write(tempDirectory1.resolve("2.proto"), "2".getBytes(StandardCharsets.UTF_8));
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.delete(file1);
        Files.delete(file2);
        Files.delete(tempDirectory1);
        Files.delete(tempDirectory2);
    }

    @Test
    public void testRead() throws Exception {
        LocalFileReader reader = new LocalFileReader(tempDirectory1, tempDirectory2);
        CharStream a = reader.read("1.proto");
        CharStream b = reader.read("2.proto");
        CharStream c = reader.read("3.proto");
        assertNotNull(a);
        assertNotNull(b);
        assertNull(c);
        assertEquals("1", a.getText(Interval.of(0, 1)));
        assertEquals("2", b.getText(Interval.of(0, 1)));
    }

}