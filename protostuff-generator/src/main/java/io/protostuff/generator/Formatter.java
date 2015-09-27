package io.protostuff.generator;

/**
 * Formats a string.
 *
 * @author Kostiantyn Shchepanovskyi
 * @author David Yu
 */
public class Formatter {

    private Formatter() {
    }

    public static String toUpperCase(String source) {
        return source.toUpperCase();
    }

    public static String toLowerCase(String source) {
        return source.toLowerCase();
    }

    /**
     * some_foo/SomeFoo becomes someFoo
     */
    public static String toCamelCase(String source) {
        return toCamelCaseImpl(source).toString();
    }

    /**
     * someFoo/SomeFoo becomes some_foo
     */
    public static String toUnderscoreCase(String source) {
        return toUnderscoreCaseImpl(source).toString();
    }

    /**
     * some_foo/someFoo becomes SomeFoo
     */
    public static String toPascalCase(String source) {
        return toPascalCaseImpl(source).toString();
    }

    private static StringBuilder toCamelCaseImpl(String name) {
        StringBuilder buffer = new StringBuilder();
        int toUpper = 0;
        char c;
        for (int i = 0, len = name.length(); i < len; ) {
            c = name.charAt(i++);
            if (c == '_') {
                if (i == len)
                    break;
                if (buffer.length() != 0)
                    toUpper++;
            } else if (toUpper != 0) {
                if (c > 96 && c < 123) {
                    buffer.append((char) (c - 32));
                    toUpper = 0;
                } else if (c > 64 && c < 91) {
                    buffer.append(c);
                    toUpper = 0;
                } else {
                    while (toUpper > 0) {
                        buffer.append('_');
                        toUpper--;
                    }
                    buffer.append(c);
                }
            } else {
                if (buffer.length() == 0 && c > 64 && c < 91)
                    buffer.append((char) (c + 32));
                else
                    buffer.append(c);
            }
        }
        return buffer;
    }

    private static StringBuilder toPascalCaseImpl(String name) {
        StringBuilder buffer = toCamelCaseImpl(name);
        char c = buffer.charAt(0);
        if (c > 96 && c < 123)
            buffer.setCharAt(0, (char) (c - 32));

        return buffer;
    }

    private static StringBuilder toUnderscoreCaseImpl(String name) {
        StringBuilder buffer = new StringBuilder();
        boolean toLower = false, appendUnderscore = false;
        for (int i = 0, len = name.length(); i < len; ) {
            char c = name.charAt(i++);
            if (c == '_') {
                if (i == len)
                    break;
                if (buffer.length() != 0)
                    appendUnderscore = true;

                continue;
            }

            if (appendUnderscore)
                buffer.append('_');

            if (c > 96 && c < 123) {
                buffer.append(c);
                toLower = true;
            } else if (c > 64 && c < 91) {
                if (toLower) {
                    // avoid duplicate underscore
                    if (!appendUnderscore)
                        buffer.append('_');
                    toLower = false;
                }
                buffer.append((char) (c + 32));
            } else {
                buffer.append(c);
                toLower = false;
            }
            appendUnderscore = false;
        }
        return buffer;
    }

}


