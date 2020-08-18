package com.spring.core;

import com.sun.istack.internal.Nullable;

public abstract class NestedExceptionUtils {

    /**
     * build a message for the given base message and root cause.
     *
     * @param message
     * @param cause
     * @return
     */
    public static String buildMessage(@Nullable String message, @Nullable Throwable cause) {
        if (cause == null) return message;
        StringBuilder sb = new StringBuilder(64);
        if (message != null) {
            sb.append(message).append("; ");
        }
        sb.append("nested exception is ").append(cause);
        return sb.toString();
    }

    public static Throwable getRootCause(@Nullable Throwable original) {
        if (original == null) return null;
        Throwable rootCause = null;
        Throwable cause = original.getCause();
        while (cause != null && cause != rootCause) {
            rootCause = cause;
            cause = cause.getCause();
        }
        return rootCause;
    }

    public static Throwable getSpecificCause(Throwable original) {
        Throwable rootCause = getRootCause(original);
        return (rootCause != null ? rootCause : original);
    }

}
