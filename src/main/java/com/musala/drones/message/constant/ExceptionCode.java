package com.musala.drones.message.constant;

public final class ExceptionCode {
    public static final String UNEXPECTED_ERROR = "Exception.unexpected";
    public static final String CONSTRAINT_VIOLATION_EXCEPTION = "Exception.ConstraintViolationException";
    public static final String EMPTY_RESULT_DATA_ACCESS_EXCEPTION = "Exception.EmptyResultDataAccessException";

    public static final String BAD_CREDENTIALS = "error.authentication.badCredentials";
    public static final String DISABLED = "error.authentication.disabled";
    public static final String ACCESS_DENIED = "error.authentication.AccessDenied";

    private ExceptionCode() {
        throw new IllegalStateException("ExceptionCode class");
    }
}
