package com.zwb.next.util;

/**
 * 字符串工具类
 * @author zhouwb
 */
public final class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean equal(String str1, String str2) {
        return _equal(str1, str2, true);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return _equal(str1, str2, false);
    }

    private static boolean _equal(String str1, String str2, boolean isCaseSensitive) {
        return str1 == null ? str2 == null : (isCaseSensitive? str1.equals(str2) : str1.equalsIgnoreCase(str2)) ;
    }

}
