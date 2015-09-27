package io.protostuff.generator.java;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.protostuff.compiler.model.DynamicMessage;
import io.protostuff.compiler.model.Proto;
import io.protostuff.generator.GeneratorInfo;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoUtil {

    public static final String OPTION_JAVA_PACKAGE = "java_package";

    private static final LocalDate DATE = LocalDate.now();

    public static GeneratorInfo getGeneratorInfo(Proto proto) {
        return GeneratorInfo.newBuilder()
                .name(JavaGenerator.class.getCanonicalName())
                .date(DATE.format(DateTimeFormatter.ISO_DATE))
                .version(JavaGenerator.class.getPackage().getImplementationVersion())
                .build();
    }

    public static String getPackage(Proto proto) {
        DynamicMessage.Value javaPackage = proto.getOptions().get(OPTION_JAVA_PACKAGE);
        if (javaPackage != null) {
            return javaPackage.getString();
        }
        return proto.getPackage().getValue();
    }

    public static String getPackagePath(Proto proto) {
        String javaPackage = getPackage(proto);
        return javaPackage.replace('.', '/');
    }
}
