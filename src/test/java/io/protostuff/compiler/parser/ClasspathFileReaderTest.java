package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.CharStream;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ClasspathFileReaderTest {

    @Test
    public void testRead() throws Exception {
        ClasspathFileReader reader = new ClasspathFileReader();
        CharStream a = reader.read("test/nested_messages/test.proto");
        CharStream b = reader.read("this_file_does_not_exist.proto");
        assertNotNull(a);
        assertNull(b);
    }
}
