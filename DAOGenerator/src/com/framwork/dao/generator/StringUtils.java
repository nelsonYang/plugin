package com.framwork.dao.generator;

/**
 *
 * @author nelson
 */
public class StringUtils {

    public static String toFirstUpcase(String str) {
        String result = str;
        char c = str.charAt(0);
        char first = 0;
        if (c >= 'a' && c <= 'z') {
            first = (char) (c - 32);
            result = first + str.substring(1);
        }

        result = result.replaceAll("_", "");
        return result;
    }
}
