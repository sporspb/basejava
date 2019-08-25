package com.spor.webapp.util;

public class StringUtil {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
}
