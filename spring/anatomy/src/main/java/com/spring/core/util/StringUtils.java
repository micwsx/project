package com.spring.core.util;

import com.sun.istack.internal.Nullable;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.StringValueHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.StringJoiner;

public abstract class StringUtils {

    private static final String[] EMPTY_STRING_ARRAY = {};
    private static final String FOLDER_SEPARATOR = "/";
    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
    private static final String TOP_PATH = "..";
    private static final String CURRENT_PATH = ".";
    private static final char EXTENSION_SEPARATOR = '.';


    public static boolean hasLength(@Nullable String str) {
        return (str != null && !str.isEmpty());
    }

    public static boolean hasText(@Nullable String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(i)) {
                return true;
            }
        }
        return false;
    }

    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return (!CollectionUtils.isEmpty(collection) ? collection.toArray(StringUtils.EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY);
    }

    public static String arrayToDelimitedString(@Nullable Object[] arr,String delim){
        if (ObjectUtils.isEmpty(arr))
            return "";
        if (arr.length==1){
            return ObjectUtils.nullSafeToString(arr[0]);
        }

        StringJoiner stringJoiner=new StringJoiner(delim);
        for (Object o : arr) {
            stringJoiner.add(String.valueOf(o));
        }
        return  stringJoiner.toString();
    }
}
