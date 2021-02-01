package com.zwb.next.util;

/**
 * 字符串工具类
 * @author zhouwb
 */
public final class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
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

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
