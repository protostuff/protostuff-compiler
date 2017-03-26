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
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                if (buffer.length() != 0) {
                    toUpper++;
                }
            } else if (toUpper != 0) {
                if (isLowerCase(c)) {
                    buffer.append(toUpperCase(c));
                    toUpper = 0;
                } else if (isUpperCase(c)) {
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
                if (buffer.length() == 0 && isUpperCase(c)) {
                    buffer.append(toLowerCase(c));
                } else {
                    buffer.append(c);
                }
            }
        }
        return buffer;
    }

    private static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    private static StringBuilder toPascalCaseImpl(String name) {
        StringBuilder buffer = toCamelCaseImpl(name);
        char c = buffer.charAt(0);
        if (isLowerCase(c)) {
            buffer.setCharAt(0, toUpperCase(c));
        }

        return buffer;
    }

    private static char toUpperCase(char c) {
        return (char) (c - 32);
    }

    private static StringBuilder toUnderscoreCaseImpl(String name) {
        StringBuilder buffer = new StringBuilder();
        boolean toLower = false;
        boolean appendUnderscore = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                if (buffer.length() != 0) {
                    appendUnderscore = true;
                }
                continue;
            }

            if (appendUnderscore) {
                buffer.append('_');
            }

            if (isLowerCase(c)) {
                buffer.append(c);
                toLower = true;
            } else if (isUpperCase(c)) {
                if (toLower) {
                    // avoid duplicate underscore
                    if (!appendUnderscore) {
                        buffer.append('_');
                    }
                    toLower = false;
                }
                buffer.append(toLowerCase(c));
            } else {
                buffer.append(c);
                toLower = false;
            }
            appendUnderscore = false;
        }
        return buffer;
    }

    private static char toLowerCase(char c) {
        return (char) (c + 32);
    }

}


