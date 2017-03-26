package io.protostuff.compiler.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Type;
import io.protostuff.compiler.model.UsageIndex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class UsageIndexTest {

    private Injector injector;
    private Importer importer;
    private UsageIndex usageIndex;

    @BeforeEach
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ParserModule());
        importer = injector.getInstance(Importer.class);
        usageIndex = new UsageIndex();
    }

    @Test
    public void usageIndexTest() throws Exception {
        ProtoContext context = importer.importFile(new ClasspathFileReader(), "protostuff_unittest/services_sample.proto");
        Proto proto = context.getProto();
        UsageIndex index = UsageIndex.build(Collections.singletonList(proto));
        Message message = proto.getMessage("SearchRequest");
        Collection<Type> usages = index.getUsages(message);
        Assertions.assertEquals(1, usages.size());
        Assertions.assertEquals("SearchService", usages.iterator().next().getName());
    }

}
