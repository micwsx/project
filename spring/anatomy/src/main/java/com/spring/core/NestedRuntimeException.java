package com.spring.core;

import com.sun.istack.internal.Nullable;

public abstract class NestedRuntimeException extends RuntimeException {

    /**
     * Use serialVersionUID from Spring 1.2 for interoperability.
     */
    private static final long serialVersionUID = 5439915454935047936L;

    static {
        // Eagerly load the NestedExceptionUtils class to avoid classloader deadlock
        // issues on OSGi when calling getMessage(). Reported by Don Brown; SPR-5607.
        NestedExceptionUtils.class.getName();
    }

    public NestedRuntimeException(String message) {
        super(message);
    }

    public NestedRuntimeException(@Nullable String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }





}
