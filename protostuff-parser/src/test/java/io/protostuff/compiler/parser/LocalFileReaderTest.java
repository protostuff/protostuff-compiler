package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.misc.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class LocalFileReaderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File tempDirectory1;
    private File tempDirectory2;

    @Before
    public void setUp() throws Exception {
        tempDirectory1 = folder.newFolder("protostuff-test-1");
        tempDirectory2 = folder.newFolder("protostuff-test-2");
        createFile(tempDirectory1, "1.proto", "1");
        createFile(tempDirectory2, "2.proto", "2");
    }

    private void createFile(File folder, String name, String content) throws IOException {
        String file = LocalFileReader.resolveRelativeFile(folder, name);
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.print(content);
        writer.close();
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
        Assert.assertEquals("1", a.getText(Interval.of(0, 1)));
        Assert.assertEquals("2", b.getText(Interval.of(0, 1)));
    }

}