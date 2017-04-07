package io.protostuff.generator.java;

import com.google.common.collect.ImmutableSet;

/**
 * Java language constants.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class JavaConstants {

    /**
     * Set of reserved keywords for Java.
     *
     * @see <a href="http://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html"> Java
     * Language Keywords</a>
     */
    public static final ImmutableSet<String> RESERVED_KEYWORDS = ImmutableSet.<String>builder()
            .add("abstract")
            .add("continue")
            .add("for")
            .add("new")
            .add("switch")
            .add("assert")
            .add("default")
            .add("goto")
            .add("package")
            .add("synchronized")
            .add("boolean")
            .add("do")
            .add("if")
            .add("private")
            .add("this")
            .add("break")
            .add("double")
            .add("implements")
            .add("protected")
            .add("throw")
            .add("byte")
            .add("else")
            .add("import")
            .add("public")
            .add("throws")
            .add("case")
            .add("enum")
            .add("instanceof")
            .add("return")
            .add("transient")
            .add("catch")
            .add("extends")
            .add("int")
            .add("short")
            .add("try")
            .add("char")
            .add("final")
            .add("interface")
            .add("static")
            .add("void")
            .add("class")
            .add("finally")
            .add("long")
            .add("strictfp")
            .add("volatile")
            .add("const")
            .add("float")
            .add("native")
            .add("super")
            .add("while")
            .build();

    private JavaConstants() {
        throw new IllegalAccessError("Utility class");
    }
}
