package io.protostuff.parser;

import com.google.common.base.Preconditions;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@ParametersAreNonnullByDefault
public class Util {

    // path delimiters
    public static final char WINDOWS_DELIMITER = '\\';
    public static final char LINUX_DELIMITER = '/';
    public static final char QUOTE = '"';

    /**
     * Remove quotes from given string and return result.
     * If given string is not quoted, then it is returned as is.
     * If quotes are not first and last characters in the string,
     * then only text that is between quotes is returned.
     *
     * @param text given string
     * @return given string without quotes
     */
    public static String removeQuotes(String text) {
        Preconditions.checkNotNull(text, "text can not be null");
        int n = text.length();
        int a = 0;
        int b = n-1;
        while (a < n && text.charAt(a) != QUOTE) a++;
        while (b >= 0 && text.charAt(b) != QUOTE) b--;
        if (a >= b) {
            // no quotes
            return text;
        }
        return text.substring(a+1, b);
    }

    /**
     * Returns file name by given absolute or relative file location.
     */
    public static String getFileName(String fullPath)
    {
        Preconditions.checkNotNull(fullPath, "path can not be null");
        int winDelimiterPos = fullPath.lastIndexOf(WINDOWS_DELIMITER);
        int linDelimiterPos = fullPath.lastIndexOf(LINUX_DELIMITER);
        int pos = Math.max(winDelimiterPos, linDelimiterPos);
        return fullPath.substring(pos + 1, fullPath.length());
    }
}
