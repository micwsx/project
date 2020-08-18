package com.spring.core.util;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringJoiner;

public abstract class ObjectUtils {

    private static final int INITIAL_HASH = 7;
    private static final int MULTIPLIER = 31;

    private static final String EMPTY_STRING = "";
    private static final String NULL_STRING = "null";
    private static final String ARRAY_START = "{";
    private static final String ARRAY_END = "}";
    private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;
    private static final String ARRAY_ELEMENT_SEPARATOR = ", ";
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    public static boolean isEmpty(@Nullable Object[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean containsElement(@Nullable Object[] array, Object element) {
        if (array == null) return false;
        for (Object o : array) {
            if (nullSafeEquals(o, element)) {
                return true;
            }
        }
        return false;
    }

    public static boolean nullSafeEquals(@Nullable Object o1, @Nullable Object o2) {
        if (o1 == o2)
            return true;
        if (o1 == null || o2 == null)
            return false;
        if (o1.equals(o2))
            return true;
        if (o1.getClass().isArray() && o2.getClass().isArray())
            return arrayEquals(o1, o2);
        return false;
    }

    private static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[]) o1, (Object[]) o2);
        }
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[]) o1, (byte[]) o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        return false;
    }

    public static int nullSafeHashCode(@Nullable Object[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (Object element : array) {
            hash = MULTIPLIER * hash + nullSafeHashCode(element);
        }
        return hash;
    }

    public static int nullSafeHashCode(@Nullable boolean[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (boolean element : array) {
            hash = MULTIPLIER * hash + Boolean.hashCode(element);
        }
        return hash;
    }

    public static int nullSafeHashCode(@Nullable byte[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (byte element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int nullSafeHashCode(@Nullable char[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (char element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int nullSafeHashCode(@Nullable double[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (double element : array) {
            hash = MULTIPLIER * hash + Double.hashCode(element);
        }
        return hash;
    }

    public static int nullSafeHashCode(@Nullable Object obj) {
        if (obj == null)
            return 0;
        if (obj.getClass().isArray()) {
            if (obj instanceof Object[]) {
                return nullSafeHashCode((Object[]) obj);
            }
            if (obj instanceof int[]) {
                return nullSafeHashCode((int[]) obj);
            }
            if (obj instanceof short[]) {
                return nullSafeHashCode((short[]) obj);
            }
            if (obj instanceof long[]) {
                return nullSafeHashCode((long[]) obj);
            }
            if (obj instanceof float[]) {
                return nullSafeHashCode((float[]) obj);
            }
            if (obj instanceof double[]) {
                return nullSafeHashCode((double[]) obj);
            }
            if (obj instanceof byte[]) {
                return nullSafeHashCode((byte[]) obj);
            }
            if (obj instanceof boolean[]) {
                return nullSafeHashCode((boolean[]) obj);
            }
            if (obj instanceof char[]) {
                return nullSafeHashCode((char[]) obj);
            }
        }
        return obj.hashCode();
    }

    public static int nullSafeHashCode(@Nullable float[] array) {
        if (array == null) return 0;
        int hash = INITIAL_HASH;
        for (float element : array) {
            hash = MULTIPLIER * hash + Float.hashCode(element);
        }
        return hash;
    }

    public static int nullSafeHashCode(@Nullable int[] array) {
        if (array == null) return 0;
        int hash = INITIAL_HASH;
        for (int element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int nullSafeHashCode(@Nullable long[] array) {
        if (array == null) return 0;
        int hash = INITIAL_HASH;
        for (long l : array) {
            hash = MULTIPLIER * hash + Long.hashCode(l);
        }
        return hash;
    }

    public static int nullSafeHashCode(@Nullable short[] array) {
        if (array == null) return 0;
        int hash = INITIAL_HASH;
        for (short element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static String nullSafeToString(@Nullable Object obj) {
        if (obj == null) {
            return NULL_STRING;
        }
        if (obj instanceof Object[])
            return nullSafeToString((Object[]) obj);

        if (obj instanceof boolean[])
            return nullSafeToString((boolean[]) obj);

        if (obj instanceof char[])
            return nullSafeToString((char[]) obj);

        if (obj instanceof int[])
            return nullSafeToString((int[]) obj);
        if (obj instanceof long[])
            return nullSafeToString((long[]) obj);

        if (obj instanceof float[])
            return nullSafeToString((float[]) obj);
        if (obj instanceof double[])
            return nullSafeToString((double[]) obj);

        if (obj instanceof byte[])
            return nullSafeToString((byte[]) obj);
        String str = obj.toString();
        return (str != null ? str : EMPTY_STRING);
    }


    public static String nullSafeToString(@Nullable Object[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0)
            return EMPTY_ARRAY;
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (Object o : array) {
            stringJoiner.add(String.valueOf(o));
        }
        return stringJoiner.toString();
    }

    public static String nullSafeToString(@Nullable boolean[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0)
            return EMPTY_ARRAY;
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (boolean b : array) {
            stringJoiner.add(String.valueOf(b));
        }
        return stringJoiner.toString();
    }

    public static String nullSafeToString(@Nullable byte[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (byte b : array) {
            stringJoiner.add(String.valueOf(b));
        }
        return stringJoiner.toString();
    }

    public static String nullSafeToString(@Nullable char[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (char c : array) {
            stringJoiner.add('\'' + String.valueOf(c) + '\'');
        }
        return stringJoiner.toString();
    }

    public static String nullSafeToString(@Nullable int[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (int i : array) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }

    public static String nullSafeToString(@Nullable double[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (double i : array) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }

    public static String nullSafeToString(@Nullable float[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (float i : array) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }


    public static String nullSafeToString(@Nullable long[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (long i : array) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }
}
